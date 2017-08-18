-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-dontusemixedcaseclassnames
-ignorewarnings
-verbose

-keepattributes *Annotation*,EnclosingMethod, InnerClasses, Exceptions, Signature, SourceFile, LineNumberTable
-renamesourcefileattribute SourceFile

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

################################################

-dontnote android.**
-dontwarn android.**

-dontnote com.sun.**
-dontwarn com.sun.**

-dontnote sun.**
-dontwarn sun.**

-dontnote java.**
-dontwarn java.**

-dontnote javax.**
-dontwarn javax.**

-dontnote org.slf4j.**
-dontwarn org.slf4j.**

-assumenosideeffects interface org.slf4j.Logger {
    public void trace(...);
    public void debug(...);
    public void info(...);
    public void warn(...);
    public void error(...);

    public boolean isTraceEnabled(...);
    public boolean isDebugEnabled(...);
    public boolean isWarnEnabled(...);
}

-assumenosideeffects class org.slf4j.LoggerFactory {
    public static ** getLogger(...);
}

# keep all public classes in main package
-keep public class at.favre.tools.dice.RndTool { public *; }

-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault