# RTLRecyclerDotsIndicator
An android library help you to create VirewPager decoration to your RecyclerView, it supports both LTR and RTL layouts, with customized active and inactive items decoration colors.

# Usage 

you just need to add only one line of code to turn your recyclerView into a ViewPager

`recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration(View.LAYOUT_DIRECTION_RTL));`

and in case you want to change the items circle decoration color you can pass these three arguments to the `CirclePagerIndicatorDecoration` constructor 


`CirclePagerIndicatorDecoration(int layoutDirection, int colorActive, int colorInactive)`


# Installation 

to add it to your project just add this to your dependencies in your app gradle file 

`implementation 'com.ezaldeen99.recyclerdotsdecortation:recyclerdotsdecortation:0.0.4'`
