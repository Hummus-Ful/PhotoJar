# PhotoJar
Search and list duplicate and similar photos in a given directory and all sub-directories. 

## Description
We live in a time where we share and store endless amounts of photos.<br/>
This tool was built becuase I had to organize my family photos in a hard-drive and I found many duplicated photos. For example:<br/>
If I take a photo on my phone and share it with my partner using WhatsApp, a copy (not exact copy though!) of that photo was created on their phone. Backing up these duplicates photos will consume much needed hard-disk space.<br/>
Now, you can try locating these duplicates using hash (MD5 for example), yet every small change to the photo will create a significant different hash value. WhatApp for example will remove EXIF data and lower the photo resolution. herefore the hash will be different.<br/>
This application is using [Perceptual hashing](https://www.phash.org/) and other techniques to list similar photos within a given directory and its sub-directories, and put list the similar photos that you can delete.<br/>
(Examples TBD)

## Getting Started
### Pre-Requisites and Dependencies
* Java Runtime Environment(JRE) version 11 or above. Use `java --version` to check the installed version on your machine
* Open-source dependencies are listed in the [build.gradle](https://github.com/Hummus-Ful/PhotoJar/blob/master/build.gradle) file

### Installation
* Clone the repo `git https://github.com/Hummus-Ful/PhotoJar.git`
* Change directory into the project directory `cd ./PhotoJar/`
* Build the jar `./gradlew build jar`
* Copy the `PhotoJar-VERSION.jar` file from the `build/lib/` directory (you can leave it there and simply run it)

### Usage
* locate the path of the root directory/directories you want to scan
* Run the tool and provide the path/s `java -jar /path/to/PhotoJar-VERSION.jar /path/to/dir1 /path/to/another/dir2`
* The tool will output: 
  * HTML + text files for *exact* duplicates
  * HTML + text files for *simliar* photos (look the same but has different hash value)

## Contributing
Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.
If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement". Don't forget to give the project a star! Thanks again!

## License
This project is licensed under the MIT License - see the [LICENSE](https://github.com/Hummus-Ful/PhotoJar/blob/master/LICENSE) file for details

<!---
## Authors
TBD

## Version History
TBD

## Acknowledgments
TBD
-->

## Special Thank You
To [Tutanota](https://tutanota.com) for [supporting Open-Source projects](https://tutanota.com/blog/posts/tutanota-for-open-source-teams/) just like this one.
<br/><img src="https://tutanota.com/resources/images/press/tutanota-logo-red.svg" width="100" height="120"/>

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- https://github.com/othneildrew/Best-README-Template/blob/master/README.md -->
