# CEIDG Captcha
***This project should be used only for educational purposes!***
## What is CEIDG Captcha?
CEDIG Captcha is a captcha located on [CEIDG Site](https://prod.ceidg.gov.pl/ceidg.cms.engine/). You can find it [here](https://prod.ceidg.gov.pl/ceidg/ceidg.public.ui/Search.aspx).

![CEIDG Captcha](https://i.imgur.com/iTujmQz.png)

# What this project does?
This project allows you to break Captcha located on CEIDG site with around 93.3% efficiency.

## How to compile project?
You need to clone repository by typing:
```
git clone https://github.com/scaliby/ceidg-captcha.git
```
Then you need to build project's executable `jars` by typing `./gradlew buildExec` in root folder of project.

# CEIDG Captcha Downloader
## What is this?
This module allows you to download set of images with same letter located on CEIDG site (e.g. set of 1000 images)
## How to use this?
 1. Run `ceidg-captcha-downloader` module jar by typing `java -jar ceidg-captcha-downloader/build/libs/ceidg-captcha-downloader-all-0.0.1.jar` in root folder of project.
 1. You will be prompted to select `CaptchaStore` (folder where captcha images will be stored).
 1. Type letter that you see on image in newly created window. This will be used to make directory inside selected `CaptchaStore` and later for labeling in machine learning process.
 1. After those steps you need to wait few seconds - captcha images are beeing downloaded and you will see them in selected `CaptchaStore`.

# CEIDG Captcha Machine Learning
## What is this?
This module allows you to train your own deeplearning4j neural network using captcha images downloaded in previous step.
## How to use this?
1. Run `ceid-captcha-machine-learning` module jar by typing `java -jar ceidg-captcha-machine-learning/build/libs/ceidg-captcha-machine-learning-all-0.0.1.jar` in root folder of project.
1. You will be prompted to select `CaptchaStore` (this what you've prepared in previous step). `CaptchaStore` should contains exactly 27 folders with images (this is number of images that CEIDG Site can generate). This captcha store will be used for training.
1. After selecting newly created network will be trained (this takes a little while - on my computer around 8 hours)
1. Select folder where you want to save trained network.
1. Then you need to select `CaptchaStore` again. This captcha store will be used for testing and can be the same as store used for training.
1. Trained network will be tested and training results should appear on your console.

# CEIDG Captcha REST
## What is this?
`ceidg-captcha-rest` module allows you to run trained network as REST API.
## How to use this?
1. Run `ceidg-captcha-rest` by typing `java -jar ceidg-captcha-rest/build/libs/ceidg-captcha-rest-all-0.0.1.jar` in root folder of project
1. Your server will be started by default on `http://localhost:8080`
1. Create `multipart/form-data` request from your favourite HTTP client. Parameter with image must be named `image`
1. In response you will get predicted letter from given captcha image.
![Captcha breaking demo](https://i.imgur.com/FUYeOg5.png)
