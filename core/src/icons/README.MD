## Why Kotlin Icons?

We use Kotlin Icons instead of Drawables or SVGs

This is simply to make it easier and more streamlined to work with in KMP, since it is changing a lot. Currently SVG is supported for all platforms but Android so we would be forced to convert everything to XML just for Android which is overkill.

## How to Convert?

[SVGToCompose](https://www.composables.com/svgtocompose)

After converting:
- Remove the Preview -> Preview has error in Apple
- Add package `core.icons.generated`
- Reformat code

## Note

We keep the original svgs as well, in case that in the future this method fails or we want to change the way of working

Original SVGs are stored in: `./svgs` <br />
Generated Kotlin Icons are stored in: `./generated`