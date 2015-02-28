om-react-pixi
=============

![Interactive example](docs/clickablesprites.png)

Bindings for [Om](https://github.com/swannodette/om) to let you create and
control [Pixi.js](https://github.com/GoodBoyDigital/pixi.js/) sprites from ClojureScript.

A simple example displaying text on a 400px by 300px canvas:

```
(defn simplestage [cursor]
  (om/component
   (pixi/stage #js {:width 400 :height 300}
    (pixi/text #js {:x 100 :y 100 :text "argh!"}))))
```

## How to Include and Use

The latest version of 0.3.0. In your `project.clj` include this dependency:

```
[org.clojars.haussman/om-react-pixi "0.3.0"]
```

This will also include [react-pixi](https://github.com/Izzimach/react-pixi/)
which itself includes react 0.12.2.

Standard components are in the `omreactpixi.core` namespace. There are also abbreviated forms that are based on the
forms used by [om-tools](https://github.com/Prismatic/om-tools) which are located in `omreactpixi.abbrev`.

In either case you may want to pull in the functions using the `:as` form:

```
            [omreactpixi.abbrev :as pixi]
```

so that you can refer to components using (for example) `pixi/stage` or `pixi/sprite`.

## Provided Forms

Includes the standard items from [Pixi.js](https://github.com/GoodBoyDigital/pixi.js/):

- Stage
- Sprite
- TilingSprite
- Text
- BitmapText
- DisplayObjectContainer

## Running the examples

Build the examples using cljsbuild

```
lein cljsbuild once
```

The examples can be driven by figwheel, allowing you to edit the interactive
example source and auto-reload

```
lein figwheel interactive
```

## Running Tests

Tests are run inside slimerjs.

```
npm install slimerjs
lein cljsbuild test
```

