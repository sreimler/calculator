# TDD MVP Calculator

This repository shows the [test-driven development](https://en.wikipedia.org/wiki/Test-driven_development) of a simple calculator. The presentation layer was structured following a [model-view-presenter](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93presenter) approach in order to improve the testability of the app components.

The model and view components contain pure Java code and are therefore tested with plain JUnit tests. Dependencies to other objects are mocked using [Mockito](http://mockito.org/) so that classes can be tested independent of the implementation of other project classes.

The view does only take the user input and forward it to the presenter. [Espresso](https://google.github.io/android-testing-support-library/docs/espresso/) tests validate correct functionality of the user interface.

The full app can be downloaded from the [Google Play Store](https://play.google.com/store/apps/details?id=com.sreimler.calculator).

<img src="https://raw.githubusercontent.com/sreimler/calculator/master/images/screenshot3.png" width="256" alt="Screenshot">
