<h1 align="center">
  Infinity For Reddit Fork </a>
  
</h1>

## Changes

This Fork enables a user to login via the official Reddit Accounts endpoint (no 3P authorize). The App will make requests that look like they are coming from the official Reddit App.

#### External Videos

-   External videos like REDGifs will be loaded from the Reddit servers and not through an external API if possible.
-   You will be able to see the content of broken links, if they were mirrored to Reddit's servers before breaking.
-   This only works if you go to the subreddit directly. Support for Home/Popular feeds TBD.
-   Might break if the GQL API changes, also not tested with imgur, gfycat.

## ⚠️ Warning

Asking users to input their credentials directly in the app is a big **security risk**. As a user you are also **violating the Reddit User Agreement**.

I implore everyone who uses this fork and similar projects to look at the commits yourself and ensure that the credentials are not being stolen.

The releases are automated.

## Want to input your own Client-ID?

Check out the [Sub-Branch](https://github.com/KhoalaS/Infinity-For-Reddit/tree/sub). You will be prompted to enter a Client-ID during login. The User-Agent will be determined by the two additional inputs, App name and Username.

https://github.com/KhoalaS/Infinity-For-Reddit/assets/83372697/5c752092-3f11-4e1d-a309-f8f7178a266c

## TODO (First Party Features)

I was originally looking into using the Sendbird SDK for chats, but recently Reddit changed their chat backend. They are now using [Matrix](https://github.com/matrix-org/matrix-android-sdk2).

-   [ ] Chat
-   [ ] Inline Subreddit Emotes, (needs some richtext parser, SpannableString stuff)
-   [ ] use GQL API for Home posts
-   [ ] use GQL API for Popular/All posts
-   [x] use GQL API for Subreddit posts
    -   [x] use the 1P Reddit DASH streams for external videos, i.e. REDGifs will load without a REDGifs API-Key.
    -   [x] fix video downloads missing audio
-   [ ] update to new federated GQL API

---

<div align="center">

A Reddit client on Android written in Java. It does not have any ads and it features a clean UI and smooth browsing experience

<img align="right" src="https://raw.githubusercontent.com/Docile-Alligator/Infinity-For-Reddit/master/fastlane/metadata/android/en-US/images/icon.png" width=200>

</div>

<br>

<div align="center">

Infinity for Reddit is available on Google Play and F-Droid

  <a href="https://play.google.com/store/apps/details?id=ml.docilealligator.infinityforreddit">
      <img alt="Get it on Google Play" height="80" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" />
      </a>  
      <a href="https://f-droid.org/packages/ml.docilealligator.infinityforreddit/">
          <img alt="Get it on F-Droid" height="80" src="https://f-droid.org/badge/get-it-on.png" />
  </a>

</div>

<div align="center">

<br>
    <a href="https://github.com/Docile-Alligator/Infinity-For-Reddit/wiki"><strong>Explore the docs »</strong></a>
<br>

<a href="https://github.com/Docile-Alligator/Infinity-For-Reddit/issues">Report a Bug</a>
·
<a href="https://github.com/Docile-Alligator/Infinity-For-Reddit/discussions/categories/ideas">Request a Feature</a>
·
<a href="https://github.com/Docile-Alligator/Infinity-For-Reddit/discussions/categories/q-a">Ask a Question</a>

</div>

<br>

<div align="center">

[![release](https://img.shields.io/github/v/release/Docile-Alligator/Infinity-For-Reddit)](https://github.com/Docile-Alligator/Infinity-For-Reddit/releases)
[![license](https://img.shields.io/github/license/Docile-Alligator/Infinity-For-Reddit)](LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/Docile-Alligator/Infinity-For-Reddit)](https://github.com/Docile-Alligator/Infinity-For-Reddit/issues)

</div>

## Donation

<p>Infinity for Reddit+:</p>
<a href="https://play.google.com/store/apps/details?id=ml.docilealligator.infinityforreddit.plus">
    <img alt="Get it on Google Play" height="80" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" />
</a>

Patreon: https://www.patreon.com/docile_alligator

Bitcoin: bc1qxtkd5ap9na7uy8nr9qpt6jny6tdwaj4v43ddle

<details>
  <summary>Table of Contents</summary>

-   [About](#about)
    -   [Built With](#built-with)
-   [Getting Started](#getting-started)
    -   [Prerequisites](#prerequisites)
    -   [Usage](#usage)
        -   [Cookiecutter template](#cookiecutter-template)
        -   [Manual setup](#manual-setup)
        -   [Variables reference](#variables-reference)
-   [Roadmap](#roadmap)
-   [Contributing](#contributing)
-   [Support](#support)
-   [License](#license)
-   [Acknowledgements](#acknowledgements)

</details>

---

## About The Project

<table>
<tr>
<td>

Key features of **Infinity For Reddit**:

-   Lazy mode: Automatic scrolling of posts enables you to enjoy amazing posts without moving your thumb.
-   Browsing posts
-   View comments
-   Expand and collapse comments section
-   Vote posts and comments
-   Save posts
-   Write comments
-   Edit comments and delete comments
-   Submit posts (text, link, image and video)
-   Edit posts (mark and unmark NSFW and spoiler and edit flair) and delete posts
-   See all the subscribed subreddits and followed users
-   View the messages
-   Get notifications of unread messages
-   etc...

</td>
</tr>
</table>

<img 
  src="https://raw.githubusercontent.com/Wladefant/Infinity-For-Reddit/master/fastlane/metadata/android/en-US/images/phoneScreenshots/1.png" 
  alt="Screenshot 1"
  height="200" >
<img 
  src="https://raw.githubusercontent.com/Wladefant/Infinity-For-Reddit/master/fastlane/metadata/android/en-US/images/phoneScreenshots/2.png" 
  alt="Screenshot 2"
  height="200" >
<img 
  src="https://raw.githubusercontent.com/Wladefant/Infinity-For-Reddit/master/fastlane/metadata/android/en-US/images/phoneScreenshots/3.png" 
  alt="Screenshot 3"
  height="200" >
<img 
  src="https://raw.githubusercontent.com/Wladefant/Infinity-For-Reddit/master/fastlane/metadata/android/en-US/images/phoneScreenshots/4.png" 
  alt="Screenshot 4"
  height="200" >
<img 
  src="https://raw.githubusercontent.com/Wladefant/Infinity-For-Reddit/master/fastlane/metadata/android/en-US/images/phoneScreenshots/5.png" 
  alt="Screenshot 5"
  height="200" >
<img 
  src="https://raw.githubusercontent.com/Wladefant/Infinity-For-Reddit/master/fastlane/metadata/android/en-US/images/phoneScreenshots/6.png" 
  alt="Screenshot 6"
  height="200" >
<img 
  src="https://raw.githubusercontent.com/Wladefant/Infinity-For-Reddit/master/fastlane/metadata/android/en-US/images/phoneScreenshots/7.png" 
  alt="Screenshot 7"
  height="200" >

<p align="right">(<a href="#top">back to top</a>)</p>

## Contributing

First off, thanks for taking the time to contribute! Contributions are what makes the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request.
It's better to also open an issue describing the issue you want to fix. But it is not required.

Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

Here are other ways you can help:

-   [Report Bugs](https://github.com/Docile-Alligator/Infinity-For-Reddit/issues)
-   [Make Suggestions](https://github.com/Docile-Alligator/Infinity-For-Reddit/discussions)
-   [Translate The App](https://poeditor.com/join/project?hash=b2IRyfaJv6)

<p align="right">(<a href="#top">back to top</a>)</p>

## License

Distributed under the AGPL-3.0 License. See <a href="https://github.com/Docile-Alligator/Infinity-For-Reddit/blob/master/LICENSE">LICENSE</a> for more information.

<p align="right">(<a href="#top">back to top</a>)</p>

## Contact

[u/Hostilenemy](https://www.reddit.com/user/Hostilenemy) -
docilealligator.app@gmail.com (Owner)

or [u/Wladefant](https://www.reddit.com/user/Wladefant) - wladefant@gmail.com (Collaborator)

Project Link: [https://github.com/Docile-Alligator/Infinity-For-Reddit](https://github.com/Docile-Alligator/Infinity-For-Reddit)

<p align="right">(<a href="#top">back to top</a>)</p>
