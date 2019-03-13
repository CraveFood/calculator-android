
<h1 align="center">
  <img src="https://avatars1.githubusercontent.com/u/16109321?s=200&v=4" width="150">
  <br>
  Calculator Android Fragment
</h1>

<h4 align="center">An open source calculator created by <a href="https://cravehq.com/" target="_blank">Crave</a>.</h4>

<p align="center">
  <a href="#key-features">Key Features</a> •
  <a href="#how-to-use">How To Use</a> •
  <a href="#license">License</a>
</p>
	
<p align="center">
	<img src="https://user-images.githubusercontent.com/17592866/54139204-cefe3b00-43ff-11e9-89f9-56eed399c6f5.gif" width="250">
</p>

## Key Features
* Evaluating expressions
  - The calculator implements a small library <a href="https://www.objecthunter.net/exp4j/index.html">exp4j</a>, which implements <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Dijkstra's Shunting Yard Algorithm</a>
* Fragment
  - Use it easily in Activities and Fragments
  - Give it a delegate to be called when any calculation update happens
* Kotlin language
  - Implemented using Kotlin language

## How To Use

Add the <a href="https://jitpack.io/">JitPack</a> in your project level `build.gradle` file:

```gradle
allprojects {
	repositories {
    // ...
		maven { url 'https://jitpack.io' }
    // ...
	}
}
```

After that , add the library as a dependency to your app level build.gradle file. Check the latest <a href="https://github.com/CraveFood/calculator-android/releases">release</a>

```gradle
dependencies {
    // ...
	implementation "com.github.CraveFood:calculator-android:0.0.4"
    // ...
}
```
Sync your project.<br>
Now you can use the fragment by calling:
```kotlin
CalculatorFragment.newInstance { total -> // total is a Double
  Toast.makeText(this, "Total $total", Toast.LENGTH_SHORT).show() // Example code
}
```

## License

```
MIT License

Copyright (c) 2019 Crave

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
