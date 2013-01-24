# Egg &mdash; the Applicasa Sample App for 	Android

This is a sample app provided to show developers what Applicasa looks like in action, and help them see a real example of integrating Applicasa into their applications.

## About Applicasa

Applicasa is a Mobile Game Management Platform that provides developers a series of frameworks and a custom SDK for iOS and Android platforms that provide extensive support for In-App Purchases, Promotions, Analytics, and a customizable backend datastore. You can find out more information at [the Applicasa website](http://applicasa.com).

## About Egg

Egg is an imagined game that shows common scenarios and code samples that developers can learn from and emulate in their own applications.

### What New (24.1.2013):
1. Implementation of new Facebook version
2. Bug fixes
3. Chat messages after finding users by radius
 

### What New:
1. Implementation of Android's New In app Billing (ver 3)

### What You Will Find in Egg:
1. Integrating the Applicasa SDK and frameworks for Android
2. Implementing Applicasa's extensive In-App Purchasing support
  * Virtual Currency support &mdash; whereby users use IAP to purchase in-game currency
  * Virtual Goods support &mdash; whereby users use in-game currency to buy virtual items
  * User Inventory &mdash; whereby users can see the virtual items they've purchased
3. Implementing User handling via Applicasa's SDK and Facebook
  * Register new users
  * Login/Logout via Applicasa
  * Login/Logout via Facebook
4. Implementing Promotion handling via Applicasa's Promotion framework
  * Easy-to-use promotions that are triggered by events that happen inside your game
5. Implementing Push Messages 
  * Send user to users push message

## Getting Started

### Clone the Repository

As usual, you get started by cloning the project to your local machine:

```
$ git://github.com/Applicasa/Sample-App---Android.git
```

### Open and Run Project in eclipse

Now that you have cloned the repo, open the project up in Eclipse. At this point, you *should* be able to build and run the project in the Android device or emulator.

### Making Test IAP Purchases

Because this is a sandboxed app, we have added Android's static In app product item. (The only item with an avatar image)

**NOTE:** You can also **download** the App from [Google Play](https://play.google.com/store/apps/details?id=com.appvilleegg)

## Android Version Targeting

Egg is currently built to work with Android API 15(Ice Cream Sandwich). **However**, Egg's minimum SDK support is 10(Gingerbread).

## Other Remarks

### A Word on Promotions

Promotions can be triggered by events inside your application.

Events that can be triggered inside Egg:
* First-time User
* First-time Virtual Currency Purchase
* First-time Virtual Good Purchase
* Low-balance Promotion

Three types of Promotions can be used in your game:
* Announcements (news updates, new app versions, etc.)
* Deals (special offers on in-app purchases & virtual goods, etc.)
* Rewards (special rewards for completing tasks in the game, etc.)

**NOTE:** The first time you make a virtual currency purchase inside Egg, you'll see a promotion for downloading another app. This shows how you can use Applicasa to cross-promote other content you have in the App and iTunes Stores or In the Google Play Store. 

### Customizing Your Applicasa Application

All customization of IAP items, Promotions, Virtual Currencies, Virtual Goods, and custom data are handled via the Applicsa web console. [Sign up for a private beta account](http://applicasa.com/#Register) to experience more.

<img src="https://raw.github.com/Applicasa/Sample-App---iOS/stable/readme_imgs/web-console.png"/>

The screenshot below is one of the steps required to create a promotion. Here it's a promo in the form of a deal, offering 20% off on another virtual good. This promo is triggered once the player has made his/her first purchase. Our promotional tool offers many different events that can trigger these kinds of promotions during the game.

<img src="https://raw.github.com/Applicasa/Sample-App---iOS/stable/readme_imgs/Offer.png"/>

### A Word on Branches

Egg is, like any Github project, a constant work-in-progress. This means that you will, if you look, notice there are other branches. We recommend that all developers who are new to Applicasa and just getting their feet wet with the SDK and frameworks stick to the ```stable``` branch only. The ```master``` branch is our main development branch, and all other branches are feature-specific branches that are currently under development and will be merged into ```stable``` when they are tested and ready. You may check out these branches at any time if you want to see other feature work and examples of how to implement those in your game (where applicable), but be aware that we don't support any branches other than ```stable``` at this time (for issues and whatnot).

### Having Trouble?

Please feel free to submit issues with any bugs or other unforseen issues you experience. We work diligently to ensure that the ```stable``` branch is always bug-free and easy to clone and run from Xcode. If you experience problems, open an issue describing the problem and how to reproduce it, and we'll be sure to take a look at it.
