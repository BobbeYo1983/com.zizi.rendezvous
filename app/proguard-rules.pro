# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#Добавлял вычитав это: #############################################################################
#When using Firebase Realtime Database in your app along with ProGuard
#you need to consider how your model objects will be serialized and deserialized after obfuscation.
#If you use DataSnapshot.getValue(Class) or DatabaseReference.setValue(Object) to read and write data
#you will need to add rules to the proguard-rules.pro file:

# Add this global rule
#-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
#-keepclassmembers class ru.zizi.rendezvous.ModelMessage { *; }
#-keepclassmembers class ru.zizi.rendezvous.ModelChat { *; }
# ==================================================================================================