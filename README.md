# asssignmentSonyLiv
This assignment has been given to me for Android developer position at SonyLiv.

### This codeis not full functional but can give a context of what I am trying to do.

## I would prefer approach 3 of using ML kit which is one time setup and it leads to zero maintainance.
## Other two methods leads us to continuous intigration and maintance.

Approach 1 :
As described into assignment details 
    My approach is to save localization strings with
    {
      "en": {
         "hello_world": "hello_world_en"
      }
    } 
    this Jso format and then retirve it ass file over network 
    I tried to setup components for Localisation and Network communication
    Addded utility methods to downlaod file and save it into local storage
    
    
Approach 2 :

There is a prebuillt library who is doing same stufff
https://localazy.com/docs/android/localazy-android-library
we can check and make usse of it.

Approach 3 :

We can use Google's ML traslation api
It supports 50+ languages.
The only draw backk I can see is bloating apk size as each model can take upto 30MB of size into device.
https://codelabs.developers.google.com/codelabs/mlkit-android-translate#0


Related Articles :
1) https://localazy.com/docs/android/localazy-android-library
2) https://developer.android.com/training/basics/supporting-devices/languages
3) https://developer.android.com/guide/topics/resources/localization
4) https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758
5) https://github.com/YarikSOffice/lingver
6) https://codelabs.developers.google.com/codelabs/mlkit-android-translate#0
