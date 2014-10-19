Weather-Catch-Android-App
=========================

Weather App showing weather forecast up to 10 days.

This app has been created as an academic assignment for course named "Software Design - handheld devices".

This App has two screens(Activities):


1. Weather
    - It shows weather forecast in a listview.
    - By default, it will show three day forecast of your current location in Fahrenheit.
    - You can also reload weather data by pressing reload button in action bar.
    - You can customize this page by using settings screen.

2. Setting
    - You can choose metric for temperature.
    - You can add zipcode and select to use zipcode for weather forecast.
    - You can also set number of days to show on first screen.
    - The user preference is persistent.(used sharedpreference)



Reference:
- I used weather underground api to fetch weather data.
- I studied AsyncTask concept, Threading concept(UI and background), parsing and layouts from Android Developer's website and professor's notes.
