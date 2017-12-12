# android-interview

## Resources Used

* Big Nerd Ranch Android Programming Guide, 3rd Edition
    * Referenced Geoquiz and CriminalIntent projects previously written with this guide
* Android Developer Documentation
    * [Services](https://developer.android.com/guide/components/services.html)
    * [Bound Services](https://developer.android.com/guide/components/bound-services.html)
* [ButterKnife Documentation](http://jakewharton.github.io/butterknife/)
* Google search
    * "android password field"
        * [Mkyong.com](https://www.mkyong.com/android/android-password-field-example/)
    * "regex"
        * [regexr](https://regexr.com/)
    * "java string.matches"
        * [stackoverflow - regex doesn't work in string matches](https://stackoverflow.com/questions/8923398/regex-doesnt-work-in-string-matches)
    * "parcelable"
        * [Parcelable vs. Java Serialization in Android App Development](https://www.3pillarglobal.com/insights/parcelable-vs-java-serialization-in-android-app-development)
        * [Using Parcelable](https://guides.codepath.com/android/using-parcelable)
    * "fragment setarguments"
        * [Handling bundles in activities and fragments](http://blog.petrnohejl.cz/handling-bundles-in-activities-and-fragments)
    * "set actionbar title"
        * [stackoverflow - setting action bar title and subtitle](https://stackoverflow.com/questions/14297178/setting-action-bar-title-and-subtitle)
    * "return value from retrofit onresponse"
        * [stackoverflow - How can I return value from onResponse of Retrofit v2](https://stackoverflow.com/questions/44872115/how-can-i-return-value-from-onresponse-of-retrofit-v2)
        * [stackoverflow - How can I return data in method from Retrofit onResponse?](https://stackoverflow.com/questions/42636247/how-can-i-return-data-in-method-from-retrofit-onresponse)
    * "activity and fragment lifecycle"
        * [The Complete Android Activity/Fragment Lifecycle](https://www.techsfo.com/blog/wp-content/uploads/2014/08/complete_android_fragment_lifecycle.png)
        
        
## Future Improvements

* Save username on destroy when the save username checkbox is checked
* Save checkbox state of skills in profile fragment view

## Known Issues

* Crashing on rotate in the profile fragment
    * On rotating the screen, the application crashes. In this case, at the time that the profile
      fragment tries to make the web service call for profile information, the service is null.
        * One attempted fix was to add the profile to the bundle in the profile fragment's 
          onSaveInstanceState and pull it out in onCreateView. I wrapped the call to the service in
          onResume in a condition so it would only call if mProfile is null, thus skipping the
          call in the case of rotation. This solved the rotation issue. However, it broke the logic
          of making a web service call for fresh data after pressing the Home button and then
          reloading the application, which worked previously.
