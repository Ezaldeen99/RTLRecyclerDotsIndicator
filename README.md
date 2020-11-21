# RTLRecyclerDotsIndicator
An android library help you to create VirewPager decoration to your RecyclerView, it supports both LTR and RTL layouts, with customized active and inactive items decoration colors.

![Alt demo](https://github.com/Ezaldeen99/RTLRecyclerDotsIndicator/blob/master/Screen_Recording_20201030-231109_1.gif)


# Usage 

you just need to add only one line of code to turn your recyclerView into a ViewPager

```Java
recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration(View.LAYOUT_DIRECTION_RTL));
```

and in case you want to change the items circle decoration color you can pass these three arguments to the `CirclePagerIndicatorDecoration` constructor 


```Java
CirclePagerIndicatorDecoration(int layoutDirection, int colorActive, int colorInactive)
```


# Installation 

to add it to your project just add this to your dependencies in your app gradle file 

```gradle
implementation 'com.ezaldeen99.recyclerdotsdecortation:recyclerdotsdecortation:0.0.4'
```
