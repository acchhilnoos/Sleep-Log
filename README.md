# My Personal Project

## A Sleep Logging/Tracking application

The idea for this project is to create an application that will allow a user to make note of their sleep
patterns by logging the time / duration of their sleep.

**What will the application do?**:
- Log hours slept
  - From xx:xx to xx:xx
  - From xx:xx for xx:xx time
- Log times when woken during sleep
- Analyze sleeping "patterns"
  - Trends across time periods
  - Indicate whether "healthy" or not

**Who will use it**?
- People who suffer from sleep disorders
- People curious about their sleeping patterns
- People who want to achieve consistency in their sleep

**Why is this project of interest to you**?
- My own sleep is terribly inconsistent ("no sleep one night, 9 hours the next" kind of thing)
  - Many of the people close to me are the same way
- A good sleep is an important part of day-to-day life

## User Stories
- As a user, I want to be able to add a night slept(/unslept) to a list of nights
- As a user, I want to be able to edit previous nights
- As a user, I want to be able to see when I woke up in the night
- As a user, I want to be able to see nights with more/less than X hours slept
- As a user, I want to be able to save my sleep log despite closing the application
- As a user, I want to be able to open my sleep log automatically upon opening the application, but still have the
option of starting a new one

# Instructions for Grader
- "You can generate the first required event related to adding Xs to a Y by" clicking the "Add Night" button
  - You will be prompted to input a start and end time in the given format
- "You can generate the second required event related to adding Xs to a Y by" clicking the "Add Interruption" button
  - You can only do so if you have already added a night of sleep
- "You can locate my visual component by" looking at the right side of the screen
- "You can save the state of my application by" clicking the Save button
- "You can reload the state of my application by" clicking the Load button

# Phase 4: Task 2
Fri Dec 02 09:54:13 PST 2022
Night added to sleep log.
Fri Dec 02 09:54:29 PST 2022
Night added to sleep log.
Fri Dec 02 09:54:43 PST 2022
Interruption added to 22:50-07:29 (1).
Fri Dec 02 09:55:07 PST 2022
Night added to sleep log.
Fri Dec 02 09:55:12 PST 2022
Interruption removed from 22:50-07:29 (1).
Fri Dec 02 09:55:28 PST 2022
Interruption added to 20:27-08:15 (1).
Fri Dec 02 09:55:30 PST 2022
Night removed from sleep log.

# Phase 4: Task 3
- Multiple classes for GUI instead of just the one
  - One per "section panel"
- Better button press handling
  - Method overloading seems unnecessary
- SleepLog is effectively a re-implementation of ArrayList
  - Might have been better to write as SleepLog extends ArrayList
- JFrame declaration could have been in SleepLogApp instead of Main
- actionPerformed could have been split
  - separate classes in ui would allow for multiple actionListeners