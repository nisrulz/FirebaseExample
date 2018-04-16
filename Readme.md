![Image](/img/github_banner.png)

### Badges/Featured In
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Firebase%20Example-brightgreen.svg?style=flat)](https://android-arsenal.com/details/3/4957#)

### Show some :heart:
[![GitHub stars](https://img.shields.io/github/stars/nisrulz/FirebaseExample.svg?style=social&label=Star)](https://github.com/nisrulz/FirebaseExample) [![GitHub forks](https://img.shields.io/github/forks/nisrulz/FirebaseExample.svg?style=social&label=Fork)](https://github.com/nisrulz/FirebaseExample/fork) [![GitHub watchers](https://img.shields.io/github/watchers/nisrulz/FirebaseExample.svg?style=social&label=Watch)](https://github.com/nisrulz/FirebaseExample) [![GitHub followers](https://img.shields.io/github/followers/nisrulz.svg?style=social&label=Follow)](https://github.com/nisrulz/FirebaseExample)  
[![Twitter Follow](https://img.shields.io/twitter/follow/nisrulz.svg?style=social)](https://twitter.com/nisrulz) 

:fire: Simplistic example app demonstrating using latest Firebase features.

Each branch contains commits specific to implementing a particular feature of firebase.

1. [Authentication](https://github.com/nisrulz/FirebaseExample/tree/auth)
1. [Crash-Reporting](https://github.com/nisrulz/FirebaseExample/tree/crash-reporting)
1. [Analytics](https://github.com/nisrulz/FirebaseExample/tree/analytics)
1. [Firebase Cloud Messaging](https://github.com/nisrulz/FirebaseExample/tree/firebase-cloud-messaging)
1. [Realtime Database](https://github.com/nisrulz/FirebaseExample/tree/realtime-db)
1. [Remote Config](https://github.com/nisrulz/FirebaseExample/tree/remote-config)

# Pull Requests
I welcome and encourage all pull requests. It usually will take me within 24-48 hours to respond to any issue or request. Here are some basic rules to follow to ensure timely addition of your request:
  1. Match coding style (braces, spacing, etc.) This is best achieved using `Reformat Code` feature of Android Studio `CMD`+`Option`+`L` on Mac and `CTRL` + `ALT` + `L` on Linux + Windows .
  2. If its a feature, bugfix, or anything please only change code to what you specify.
  3. Please keep PR titles easy to read and descriptive of changes, this will make them easier to merge :)
  4. Pull requests _must_ be made against `develop` branch. Any other branch (unless specified by the maintainers) will get rejected.
  5. Check for existing [issues](https://github.com/nisrulz/android-examples/issues) first, before filing an issue.
  6. Make sure you follow the set standard as all other projects in this repo do

      + Upgrade your gradle wrapper to the one all other apps are using. Use the below command at root of your project

          ```
          ./gradlew wrapper --gradle-version <version_name>
          ```
          i.e `./gradlew wrapper --gradle-version 4.6`

      + Use `ext` variables as defined in [`dependencies.gradle`](/dependencies.gradle), in your `build.gradle` files to make sure all apps are in sync with configurations and dependencies. Take a look [here](/DataBinding/app/build.gradle) and [here](/DataBinding/build.gradle)

      + Use the package name of the format `github.nisrulz.sample.*` where `*` is the example you are adding to the repo. I am trying to follow a set standard in the repo, please adhere to that.
  7. Have fun!
  
# Getting Started
To be able to run the app or contribute you will need to create a project on [Firebase](https://firebase.google.com/) in order to get the `google-services.json` file that is needed. You can follow the instructions for the same [here](https://firebase.google.com/docs/android/setup). Once you have this file copy it into your project's module folder, typically `app/` and sync the project.

### Created & Maintained By
[Nishant Srivastava](https://github.com/nisrulz) ([@nisrulz](https://www.twitter.com/nisrulz))

> If you found these examples helpful or you learned something from their source code and want to thank me, consider buying me a cup of :coffee:
>  + [PayPal](https://www.paypal.me/nisrulz/5)
>  + Bitcoin Address: 13PjuJcfVW2Ad81fawqwLtku4bZLv1AxCL

License
=======

    Copyright 2016 Nishant Srivastava

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
