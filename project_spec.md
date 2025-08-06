# Yatra - Local Tourist Attraction Feed (Trip Advisor / Google Maps)

## Table of Contents

1. [App Overview](#App-Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Build Notes](#Build-Notes)

## App Overview

### Description

A TripAdvisor-style app that allows users to search for interesting places to go in the city they're at (historical locations, places to eat, galleries/museums, other points of interest).

## App Evaluation

### Local Tourist Attraction Feed (Trip Advisor / Google Maps)

#### Mobile: How uniquely mobile is the product experience?

- What makes your app more than a glorified website?
  > Answer: App allows you to plan while you're on the go.

#### Story: How compelling is the story around this app once completed?

- How clear is the value of this app to your audience?
- How well would your friends or peers respond to this product idea?
  > Answer: Our peers are the target age demographic for traveling.

#### Market: How large or unique is the market for this app?

- What's the size and scale of your potential user base?
- Does this app provide huge value to a niche group of people?
- Do you have a well-defined audience of people for this app?
  > Answer: Global user base, not niche, but catered to tourists or people visiting another city (travelers / vacationers, sight-seers).

#### Habit: How habit-forming or addictive is this app?

- How frequently would an average user open and use this app?
- Does an average user just consume your app, or do they use it to create something?
  > Answer: Not habit-forming like social media. User consumes MVP, with potential for creating something as a stretch feature (itinerary, cost calculator).

#### Scope: How well-formed is the scope for this app?

- How technically challenging will it be to complete this app by the deadline?
- Is a stripped-down version of this app still interesting to build?
- How clearly defined is the product you want to build?
  > Answer: MVP makes a request to API with user input for location, filters, and displays scrollable list of results. Not technically challenging as an MVP, but still interesting to build. Clearly well-defined.

## Product Spec

### 1. User Features (Required and Optional)

Required Features:

[X] Search for points of interest by:

- City
- Filter by category(coffee, restaurants, parks...)
- Nearby using GPS
- Favourites tab

[X] Recycler view of results

[X] Bottom Navbar (dock)

Stretch Features:

- Search and book for tour groups, hotels, flights
- Interactive map

### 2. Chosen API(s)

- SerpAPI
  - https://serpapi.com/google-local-api
- Google Places API (fallback)

### 3. User Interactions

#### Main

- Searching (enter city, filter selections)
  - Result: Displays a list ofsearch results matching location and selected filters in a scrollable list.
- Navigation

#### Stretch

- Favoriting functionality
  - Result: Adds item to list of favorites
- Double-click
  - Result: Displays more detailed information about item
- Possible Map interactions
  - Button to open google maps and display location
  - Zoom in, zoom out, scroll around

## Wireframes

<!-- Add picture of your hand sketched wireframes in this section -->

![me](https://github.com/Code-Path-AND101-group-31/CodePathCapstone/blob/main/images/myat_drawn_wireframe.jpg)

![me](https://github.com/Code-Path-AND101-group-31/CodePathCapstone/blob/main/images/myat_drawn_wireframe2.jpg)

## Build Notes

For Milestone 2, include **2+ Videos/GIFs** of the build process here!

Below are two gifs that represent significant milestones in our progress in building the application.

![me](https://github.com/Code-Path-AND101-group-31/CodePathCapstone/blob/main/images/capstone1.gif)

^ The above GIF is the successful function of the search component, pulling and displaying data from the API.

![me](https://github.com/Code-Path-AND101-group-31/CodePathCapstone/blob/main/images/capstone2.gif)

^ The above GIF shows the functionality of the navigation bar at the bottom of the screen.

## License

Copyright **2025** **Ryan Lawton, Alex Surprenant, Myat Min Htoo, Saurab Gyawali**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.