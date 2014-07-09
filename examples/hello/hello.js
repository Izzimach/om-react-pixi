goog.addDependency("base.js", ['goog'], []);
goog.addDependency("../cljs/core.js", ['cljs.core'], ['goog.string', 'goog.array', 'goog.object', 'goog.string.StringBuffer']);
goog.addDependency("../om/dom.js", ['om.dom'], ['cljs.core']);
goog.addDependency("../om/core.js", ['om.core'], ['cljs.core', 'om.dom', 'goog.ui.IdGenerator']);
goog.addDependency("../clojure/string.js", ['clojure.string'], ['cljs.core', 'goog.string', 'goog.string.StringBuffer']);
goog.addDependency("../omreactpixi/core.js", ['omreactpixi.core'], ['cljs.core', 'om.core', 'clojure.string', 'om.dom', 'goog.events']);
goog.addDependency("../omreactpixi/hello.js", ['omreactpixi.hello'], ['omreactpixi.core', 'cljs.core', 'om.core', 'clojure.string', 'om.dom', 'goog.events']);