var PROJECT = "imgotv.mobile", 
VERSION = "20140730", 
DEV = !!window.DEV, 
COMBO = false, 
COMBOURL = "http://honey.hunantv.com/combo/", 
ROOT = "http://honey.hunantv.com/hunantv.mobile/js/", 
PUBROOT = "http://honey.hunantv.com/honey-2.0/", 
CSS = "http://honey.hunantv.com/hunantv.mobile/css/", 
IMG = "http://honey.hunantv.com/hunantv.mobile/image/";
(function(win, undefined) {
	"use strict";
	var doc = win.document, domWaiters = [], queue = [], handlers = {}, assets = {}, isAsync = "async" in doc
			.createElement("script")
			|| "MozAppearance" in doc.documentElement.style || win.opera, isHeadReady, isDomReady, headVar = win.head_conf
			&& win.head_conf.head || "head", api = win[headVar] = win[headVar]
			|| function() {
				api.ready.apply(null, arguments)
			}, PRELOADING = 1, PRELOADED = 2, LOADING = 3, LOADED = 4;
	if (isAsync) {
		api.load = function() {
			var args = arguments, callback = args[args.length - 1], items = {};
			if (!isFunction(callback)) {
				callback = null
			}
			each(args, function(item, i) {
				if (item !== callback) {
					item = getAsset(item);
					items[item.name] = item;
					load(item, callback && i === args.length - 2 ? function() {
						if (allLoaded(items)) {
							one(callback)
						}
					} : null)
				}
			});
			return api
		}
	} else {
		api.load = function() {
			var args = arguments, rest = [].slice.call(args, 1), next = rest[0];
			if (!isHeadReady) {
				queue.push(function() {
					api.load.apply(null, args)
				});
				return api
			}
			if (!!next) {
				each(rest, function(item) {
					if (!isFunction(item)) {
						preLoad(getAsset(item))
					}
				});
				load(getAsset(args[0]), isFunction(next) ? next : function() {
					api.load.apply(null, rest)
				})
			} else {
				load(getAsset(args[0]))
			}
			return api
		}
	}
	api.js = api.load;
	api.test = function(test, success, failure, callback) {
		var obj = typeof test === "object" ? test : {
			test : test,
			success : !!success ? isArray(success) ? success : [ success ]
					: false,
			failure : !!failure ? isArray(failure) ? failure : [ failure ]
					: false,
			callback : callback || noop
		};
		var passed = !!obj.test;
		if (passed && !!obj.success) {
			obj.success.push(obj.callback);
			api.load.apply(null, obj.success)
		} else if (!passed && !!obj.failure) {
			obj.failure.push(obj.callback);
			api.load.apply(null, obj.failure)
		} else {
			callback()
		}
		return api
	};
	api.ready = function(key, callback) {
		if (key === doc) {
			if (isDomReady) {
				one(callback)
			} else {
				domWaiters.push(callback)
			}
			return api
		}
		if (isFunction(key)) {
			callback = key;
			key = "ALL"
		}
		if (typeof key !== "string" || !isFunction(callback)) {
			return api
		}
		var asset = assets[key];
		if (asset && asset.state === LOADED || key === "ALL" && allLoaded()
				&& isDomReady) {
			one(callback);
			return api
		}
		var arr = handlers[key];
		if (!arr) {
			arr = handlers[key] = [ callback ]
		} else {
			arr.push(callback)
		}
		return api
	};
	api.ready(doc, function() {
		if (allLoaded()) {
			each(handlers.ALL, function(callback) {
				one(callback)
			})
		}
		if (api.feature) {
			api.feature("domloaded", true)
		}
	});
	function noop() {
	}
	function each(arr, callback) {
		if (!arr) {
			return
		}
		if (typeof arr === "object") {
			arr = [].slice.call(arr)
		}
		for (var i = 0, l = arr.length; i < l; i++) {
			callback.call(arr, arr[i], i)
		}
	}
	function is(type, obj) {
		var clas = Object.prototype.toString.call(obj).slice(8, -1);
		return obj !== undefined && obj !== null && clas === type
	}
	function isFunction(item) {
		return is("Function", item)
	}
	function isArray(item) {
		return is("Array", item)
	}
	function toLabel(url) {
		var items = url.split("/"), name = items[items.length - 1], i = name
				.indexOf("?");
		return i !== -1 ? name.substring(0, i) : name
	}
	function one(callback) {
		callback = callback || noop;
		if (callback._done) {
			return
		}
		callback();
		callback._done = 1
	}
	function getAsset(item) {
		var asset = {};
		if (typeof item === "object") {
			for ( var label in item) {
				if (!!item[label]) {
					asset = {
						name : label,
						url : item[label]
					}
				}
			}
		} else {
			asset = {
				name : toLabel(item),
				url : item
			}
		}
		var existing = assets[asset.name];
		if (existing && existing.url === asset.url) {
			return existing
		}
		assets[asset.name] = asset;
		return asset
	}
	function allLoaded(items) {
		items = items || assets;
		for ( var name in items) {
			if (items.hasOwnProperty(name) && items[name].state !== LOADED) {
				return false
			}
		}
		return true
	}
	function onPreload(asset) {
		asset.state = PRELOADED;
		each(asset.onpreload, function(afterPreload) {
			afterPreload.call()
		})
	}
	function preLoad(asset, callback) {
		if (asset.state === undefined) {
			asset.state = PRELOADING;
			asset.onpreload = [];
			loadAsset({
				url : asset.url,
				type : "cache"
			}, function() {
				onPreload(asset)
			})
		}
	}
	function load(asset, callback) {
		callback = callback || noop;
		if (asset.state === LOADED) {
			callback();
			return
		}
		if (asset.state === LOADING) {
			api.ready(asset.name, callback);
			return
		}
		if (asset.state === PRELOADING) {
			asset.onpreload.push(function() {
				load(asset, callback)
			});
			return
		}
		asset.state = LOADING;
		loadAsset(asset, function() {
			asset.state = LOADED;
			callback();
			each(handlers[asset.name], function(fn) {
				one(fn)
			});
			if (isDomReady && allLoaded()) {
				each(handlers.ALL, function(fn) {
					one(fn)
				})
			}
		})
	}
	function loadAsset(asset, callback) {
		callback = callback || noop;
		var ele;
		if (/\.css[^\.]*$/.test(asset.url)) {
			ele = doc.createElement("link");
			ele.type = "text/" + (asset.type || "css");
			ele.rel = "stylesheet";
			ele.href = asset.url
		} else {
			ele = doc.createElement("script");
			ele.type = "text/" + (asset.type || "javascript");
			ele.src = asset.url
		}
		ele.onload = ele.onreadystatechange = process;
		ele.onerror = error;
		ele.async = false;
		ele.defer = false;
		function error(event) {
			event = event || win.event;
			ele.onload = ele.onreadystatechange = ele.onerror = null;
			callback()
		}
		function process(event) {
			event = event || win.event;
			if (event.type === "load" || /loaded|complete/.test(ele.readyState)
					&& (!doc.documentMode || doc.documentMode < 9)) {
				ele.onload = ele.onreadystatechange = ele.onerror = null;
				callback()
			}
		}
		var head = doc["head"] || doc.getElementsByTagName("head")[0];
		head.insertBefore(ele, head.lastChild)
	}
	function domReady() {
		if (!doc.body) {
			win.clearTimeout(api.readyTimeout);
			api.readyTimeout = win.setTimeout(domReady, 50);
			return
		}
		if (!isDomReady) {
			isDomReady = true;
			each(domWaiters, function(fn) {
				one(fn)
			})
		}
	}
	function domContentLoaded() {
		if (doc.addEventListener) {
			doc
					.removeEventListener("DOMContentLoaded", domContentLoaded,
							false);
			domReady()
		} else if (doc.readyState === "complete") {
			doc.detachEvent("onreadystatechange", domContentLoaded);
			domReady()
		}
	}
	if (doc.readyState === "complete") {
		domReady()
	} else if (doc.addEventListener) {
		doc.addEventListener("DOMContentLoaded", domContentLoaded, false);
		win.addEventListener("load", domReady, false)
	} else {
		doc.attachEvent("onreadystatechange", domContentLoaded);
		win.attachEvent("onload", domReady);
		var top = false;
		try {
			top = win.frameElement == null && doc.documentElement
		} catch (e) {
		}
		if (top && top.doScroll) {
			(function doScrollCheck() {
				if (!isDomReady) {
					try {
						top.doScroll("left")
					} catch (error) {
						win.clearTimeout(api.readyTimeout);
						api.readyTimeout = win.setTimeout(doScrollCheck, 50);
						return
					}
					domReady()
				}
			})()
		}
	}
	setTimeout(function() {
		isHeadReady = true;
		each(queue, function(fn) {
			fn()
		})
	}, 300)
})(window);
(function(w, doc, undefined) {
	var H = function() {
		this.version = "2.1.0"
	};
	var has_debug_hash = w.location.hash.match(/\bdebug\b/);
	H.go = function(_mods, _fn) {
		var mods = _mods.split(","), fn = _fn || null, scripts = [];
		if ((has_debug_hash || DEV) && !w.console) {
			mods.splice(0, 0, "lib:debug")
		}
		while (mods.length)
			scripts.push(labelScript(mods.shift()));
		fn && scripts.push(fn);
		head.js.apply(w, scripts);
		return H
	};
	H.ready = head.ready;
	H.load = head.load;
	H.config = function(_configs, _fun) {
		if (H.isString(_configs) && H.isString(_fun)) {
			w[_configs] = _fun;
			return H
		}
		if (_fun === undefined && !H.isString(_configs)) {
			for ( var key in _configs) {
				w[key] = _configs[key]
			}
			return H
		}
	};
	H.def = function(_deps, _fn) {
		if (arguments.length == 1) {
			_fn = _deps;
			_deps = "";
			return _fn(H)
		}
		var deps = _deps.split(","), fn = function() {
			return _fn(H)
		};
		fn()
	};
	H.trim = function(_a) {
		return _a.replace(/^\s+|\s+$/g, "")
	};
	function labelScript(_mod_name) {
		var name = H.trim(_mod_name), is_pub = name.indexOf(":") > 0, root = is_pub ? PUBROOT
				: ROOT, script = {}, m = name.split(is_pub ? ":" : "_");
		if (m[0] === "package") {
			window.package_name = m[1]
		}
		var path = m[0] + "/" + m[1] + (DEV ? ".source" : "") + ".js";
		path = root + "/" + path + "?v" + VERSION;
		path = path.replace(/([^:])\/\//g, "$1/");
		script[name] = path;
		return script
	}
	H.ie = function() {
		var undef, v = 3, div = document.createElement("div"), all = div
				.getElementsByTagName("i");
		while (
				div.innerHTML = "<!--[if gt IE " + ++v
						+ "]><i></i><![endif]-->", all[0])
			;
		return v > 4 ? v : undef
	}();
	H.inArray = function(_a, _b) {
		for (var c = 0; c < _b.length; c++) {
			if (_b[c] == _a)
				return c
		}
		return -1
	};
	H.isString = function(_o) {
		return typeof _o === "string"
	};
	H.random = function(_min, _max) {
		return (_max - _min) * Math.random() + _min
	};
	H.css = function(_url) {
		head.js(_url)
	};
	H.delegate = function(_rules) {
		return function(_e) {
			var e = _e || window.event, target = $(e.target || e.srcElement);
			for ( var selector in _rules)
				if (target.is(selector))
					return _rules[selector].apply(target, $
							.makeArray(arguments))
		}
	};
	H.debug = w.console ? console.log : function() {
	};
	w.H = w.Honey = w.honey = w.HN = H
})(window, document);