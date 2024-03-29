## Fixing broken robots - Android Mutation Testing
### DroidConSG 2019

---

@snap[north]
## About me
@snapend


@snap[east]
![panini](assets/img/panini.png)
@snapend

@snap[west span-70]
<ul>
  <li>Matthew Vern</li>
  <li>Twitter [@panini_ja](https://twitter.com/panini_ja)</li>
  <li>Github [panpanini](https://github.com/panpanini)</li>
  <li>Mercari, Inc</li>
  <li>Software Engineer (Android)</li>
</ul>
@snapend

Note:
I'm a Software Engineer focusing on Android working at Mercari, a peer to peer market application based in Japan and America.
---

@snap[north]
## My job
@snapend

- Client Engineer
- Solving problems for our customers
- Shipping features
- Improve existing functionality

Note:
Client Engineer
provide value
tools -> ship / improve

Like many of you here today, my job as a client engineer is about providing value to our customers, by solving problems. Our main tools for solving these problems are shipping features, and improving existing functionality. But the focus here is on shipping.

---

@snap[north]
## My job
@snapend

- A non-shipped feature doesn't provide benefit
- Ship features as quick as possible

Note:
Before release, no benefit

ship fast

Architecture patterns

Open source library

CI

forgetting

Before a feature is released, the customer doesn't get any benifit out of it, so its our job to make sure we can get these features out as quick as we can. To do this, we have many tools at our disposal:

Architecture patterns to reduce the cost of reading the code

OSS libraries to reduce the work required to implement common behaviour

Continuous Integration & release pipelines to reduce the work required to release the app

This is all well and good, but there is one small detail that we're forgetting
---

@snap[north]
## My job
@snapend

- A shipped, broken feature doesn't provide benefit
- Ship _quality_ features as quick as possible

Note:
buggy feature no benefit

If we ship a feature that is buggy, or broken in some way, then this also doesn't provide benefit, and in extreme cases can actually reduce the benefit of the product. So really, we should be focused on shipping _quality_ features as quick as possible.

---

@snap[north span-100]
## Maintaining quality
@snapend

- QA |
- Code Review |
- Tests |

Note:
how ensure quality?

QA - see how others use the app

Code review - depends on other engineers

Tests - quickest feedback

so, what are some of the ways that we can ensure quality of the features we are shipping?

One big way is with a Quality Assurance team. I mean the hint is in the name, but having someone else look at the feature you are working on is great for seeing how other people use the app, and is a good way to figure out bugs and unexpected user journeys, however the feedback loop is quite long, and so can be inefficient if employed improperly.

Next, we have Code Review. Similar to QA, having another person look at the code is good for spotting small mistakes, or other oversights we may have made when implementing the feature, because hey, we're only human right? And while the feedback loop is not quite as long as QA, it still can take some time, and depends on how busy the other engineers on your team are.

Finally, we come to tests. Tests can give us the quickest feedback in a way that we as engineers can understand easily

- this code _right here_ is not doing what we expected it to do

---

@snap[north span-100]
## Maintaining quality
@snapend

- How do we know our tests are providing quality
- Use coverage to make sure that our tests are calling production code |
    - changes introduced will not break existing code |
    - new code does what it says on the tin |

Note:
do we know test are qualtiy?

Code coverage - shows how much is being run

insurance for changes

new features working as intended

So how do we know that our tests are providing quality?

Well, we can use code coverage right? by using coverage, we can show how much of our code is being run by the tests, and so we then know how safe we are when changing the code.

Code coverage can give us insurance that any changes we introduce won't break existing code

Code coverage can also give us insurance that newly added features are working as intended

---

 ## Who watches the watchmen?

Note:
But who watches the watchmen?
---

@snap[north span-100]
## Maintaining quality
@snapend

### How do we know that our *tests* are quality?


Note:
How do we know the quality of our tests?
---

@snap[north span-100]
## What are tests
@snapend

Note:
First of all, what is a test?
---

@snap[north span-100]
## What are tests
@snapend

- asserting that our assumptions about a piece of code are correct
- binary assertions of code correctness


Note:
confirming code working as we expect

"this code should behave in a certain way"

make tests pass
happy green checkmark

tests designed to fail

Unit test are a way of confirming that a certain piece of code is working the way we expect it to work.

- "this code should behave in a certain way, if it doesn't, then the test should fail"

As engineers, we're used to trying to make tests pass - if our tests don't pass then we can't ship our features, so we are always looking for that happy green checkmark to tell us that our tests are all passing.

However, what we forget is that _tests are designed to fail_. Our tests are supposed to fail to tell us that something is going wrong.

---

# Lets fail some tests

---
@snap[north span-100]
## Lets fail some tests
@snapend

- Unit tests assert code behaviour
- change code behaviour
- tests fail
- ????
- profit

Note:
confirm unit test will fail

change behaviour -> fail

why didn't any think of it earlier?

In order to confirm that a unit test is working, we should make sure that they fail when we expect them to. Our expectations is that they will fail when the code no longer behaves the same way.

So if we change the code behaviour, and our tests fail, we know that it is providing quality.

why didn't anyone think of this earlier?

---

# Mutation testing

Note:
Welcome to the world of Mutation Testing!
---

@snap[north span-100]
## Mutation testing
@snapend

- proposed by Richard Lipton in 1971
- computationally expensive, not a viable testing solution until recently

Note:
1970s

advancements in computer processing

Like all good ideas, someone has already thought of it. Mutation testing was originally proposed back in the 1970s, however it has only recently become viable thanks to advancements in computer processing.
---
@snap[north span-100]
## Mutation testing steps
@snapend

1. Create a mutant
2. Run test suite
3. Confirm if mutant was detected or not
4. Repeat

Note:
basic flow:

introduce fault

run tests collect results

confirm detected
at least 1 test

The basic flow for a mutation test is as follows:

first, we introduce a fault or mutation into the production code.

next, we run our test suite and collect the results.

and then third, we confirm if the fault was detected or not.
We consider the fault "detected" if at least 1 test in the suite has failed.

---

@snap[north span-100]
## What is a mutant?
@snapend


- A mutant is a biological entity which has undergone a change in its genetic structure.

Note:
wikipedia

have a thing, copy, make new thing

code definition

If you have a look at Wikipedia, they describe a mutant as being a biological entity which has undergone a change in its genetic structure.

Basically, you have one thing over here. you change it slightly, and then you get another thing. that other thing is a mutant of the first.

In our case, we're talking about code, so we can rewrite the definition to be something like this

---

@snap[north span-100]
## What is a mutant?
@snapend

- A mutant is a code block which has undergone a change in its structure.

Note:
We have a block of code, we change it slightly, which produces our mutant.

---?code=src/main/kotlin/SessionController.kt&lang=kotlin
@snap[north span-100]
## Creating mutations
@snapend
Note:
Alright, lets start mutating some code! Take this class as an example. There are 3 functions here:

---
@snap[north span-100]
## Creating mutations
@snapend
```
fun setSessions(sessions: List<Session>) {
    this.sessions.clear()
    this.sessions.addAll(sessions)
    requestModelBuild()
}
```

Note:
clear old sessions

add new sessions

call requestModelBuild()

setSessions(), which will clear our currently held sessions, and add the new ones passed in the parameter, and then call requestModelBuild()
---
@snap[north span-100]
## Creating mutations
@snapend
```
override fun buildModels() {
    generateModels(sessions)
        .forEach { it.addTo(this) }
}
```

Note:
generate models for sessions

add to controller

buildModels(), which generates a model for each of our sessions, and then adds that model to the controller,
---
@snap[north span-100]
## Creating mutations
@snapend
```
fun generateModels(sessions: List<Session>): List<SessionModel> {
    return sessions
        .map { session ->
            SessionModel_()
                .title(session.title)
                .imageUrl(
                    if (session.speaker.profileImage != "") {
                        session.speaker.profileImage
                    } else {
                        null
                    }
                )
        }
}
```

Note:

map session -> model

set data to model

and finally generateModels(), which will map all of our sessions to a model, and then set the session data to the model.
---

@snap[north span-100]
## Creating mutations
@snapend

 - Competent Programmer Hypothesis |
 - Coupling Effect |

Note:
randomly change code, run all tests, methodical

well researched, two theories

competent programmer Hypothesis

coupling effect

look at a couple, perceived evilness

Now we could go randomly changing code to create mutants, but remember that for each mutant we create, we have to run all the tests against it to make sure it is killed, so we want to be methodical about the changes that we make.

This is actually a fairly well researched area, so if you Google you can find lists of mutators for object-oriented languages, however the basic idea behind creating mutations follows these two theories.

The competent Programmer Hypothesis states that bugs introduced by competent programmers are more often than not small syntatical errors, which are often hard to notice during code review.

Secondly, the Coupling effect states that small issues will stack up to create big issues, so if we can reduce small issues then we can also reduce the big issues at the same time.

With that in mind, lets take a look at a couple of these as an example, arranged in order of perceived evilness, which is a metric I made up based on how difficult I think these types of faults are to find.

---
@snap[north span-100]
## Conditionals boundary
@snapend

Replaces relational operators with boundary counterpart

| Original | Mutated |
|-|-|
| < | <= |
| <= | < |
| > | >= |
| >= | > |

Note:
simple but common
conditionals boundary operator

consist of 2 things

First lets start off with a fairly simple, but common one, the conditionals boundary operator. Mutation operators generally consist of two things - the original production code, and the code it should be mutated into.
---
@snap[north span-100]
## Conditionals boundary
@snapend
``` kotlin
// original
if (currentTime < startTime) {
  // do something
}

// mutated
if (currentTime <= startTime) {
  // do something
}
```
Note:
apply to previous example, looks like this

can you see? the less than became less than or equal

slip through code view, unless test explicitly for boundary

So if we apply this to our previous code example, we come up with a mutant that looks like this. Now - thats pretty evil, this is definitely the kind of thing that could slip through code review, and this kind of edge case is unlikely to be covered by tests, unless you have gone out of your way to write a test specifically for this boundary.


---
@snap[north span-100]
## Negate Conditionals
@snapend


Negates conditional checks

| Original | Mutated |
|-|-|
|==|!=|
| != | == |
| <= | > |
| > | <= |

Note:
Negate conditionsals

patterns

reverse

Next we have the negate conditionals operator - are you seeing a pattern here? Here we take conditional checks, and reverse them.
---
@snap[north span-100]
## Negate Conditionals
@snapend

``` kotlin
// original
fun buildModels() {
  SessionModel_()
      .title(session.title)
      .imageUrl(
          if (session.speaker.profileImage != "") {
              session.speaker.profileImage
          } else {
              null
          }
      )
}

// mutated
fun buildModels() {
  SessionModel_()
      .title(session.title)
      .imageUrl(
          if (session.speaker.profileImage == "") {
              session.speaker.profileImage
          } else {
              null
          }
      )
}
```

Note:
wildly different behaviour in app

not showing speaker profile if valid image URL

This one is pretty evil too, as even just this change on its own can lead to wildly different behaviour of our app, in this case showing not showing a speaker profile whenever we have a valid image URL

---
@snap[north span-100]
## Remove void calls @emoji[smiling_imp]
@snapend

*removes* void method calls

Note:
And lastly we have the Remove void calls operator, this one straight up removes calls to void methods. just deletes them. gone.

---
@snap[north span-100]
## Remove void calls @emoji[smiling_imp]
@snapend

``` kotlin
// original
fun setSessions(sessions: List<Session>) {
    this.sessions.clear()
    this.sessions.addAll(sessions)
    requestModelBuild()
}

// mutated
fun setSessions(sessions: List<Session>) {
    this.sessions.clear()
    this.sessions.addAll(sessions)

}
```
Note:
back to code, bam no longer building models
RecyclerView not updating

not sound like big deal,

actively call functions tests

Back to our code from before, and bam, we're no longer building the models in our EpoxyController, so now our RecyclerView never gets updated.
This might sound like not that big of a deal, I think more often than not we forget to test void calls in functions, as to do so you have to properly set up mocks and actively call them.
---
@snap[north span-100]
## So what?
@snapend

Note:
We have seen how to create mutations from our code, but what does it really mean? so how does this help us? Lets take a quick look at an example.

terminal: ⌥ + fn + F12

Show code coverage
./jacocoReport.sh

should take 20 seconds

Edit code


Show coverage hasn't changed

---
@snap[north span-100]
## Why mutation testing
@snapend

- Code coverage, but better

Note:
coverage is not enough

100% coverage, change code, no change

good for finding untested codepaths

So hopefully from this small demo, you've seen why code coverage alone is not enough. Even though we had 100% code coverage, when we mutated our code, we saw that our coverage didn't change, and our tests still passed. What this tells us is that our test suite in its current state is missing test cases, so changes could sneak into our codebase, and our tests wouldn't warn us about them. Finding these un-checked code paths is what mutation testing is so useful for.

---
@snap[north span-100]
## Mutation testing
### The better way
@snapend

1. Introduce a fault into production code
2. Use code coverage to determine which tests to run
3. Run tests
4. Confirm if fault was detected or not
5. Repeat


Note:
3 mutations, 5 minutes, 15 minutes

code coverage, restrict test runs

So, for our short snippet of code, we already have 3 mutations that we need to run the test suite against. If our test suite takes 5 minutes per run which is a pretty conservative estimate, then with just these 3 mutations we already have to spend 15 minutes testing these mutations, which isn't really realistic.

instead, if we use code coverage to find out which tests are actually calling this section of code, we can restrict our test runs to only those specific tests, which greatly reduces the number of tests we have to run, and so also reduces the time spent testing mutations to a more reasonable level.

---
## That's a lot of work you expect us to do there bud

Note:
you might be thinking

another test suite

mutant -> production

shipping things quickly

a lot of work

you might be thinking
"now hold up there, this is cool and all, but are you telling me that I now need to keep *another* test suite to test my test suite? what happens if the mutant code gets into my production code base? I thought we were supposed to be shipping things quickly? this all seems like a lot of work"
and to that, I say *no*.
---

@snap[north span-100]
## Pitest
@snapend
![pitest](assets/img/pitest.png)

Note:
Let me introduce PITest!
---
@snap[north span-100]
## Pitest
@snapend

- [pitest.org](www.pitest.org)
- mutation testing system
- mutants stored in memory
- outputs pretty reports
- Gradle plugin @emoji[heart_eyes_cat]

Note:
for JVM, sweet features

generate mutations with mutator operator set

editing bytecode

mutations not saved to disk -> no prod

performance -> loading class

gradle plugin

PItest is a mutation testing system for the JVM, and it has some pretty sweet features. It will automatically generate mutations based on the operator set provided. Also, the mutations are generated by editing the bytecode of a class after it has been loaded by the ClassLoader, which means that
a) mutations are never saved to disk
b) we don't have to pay the cost of loading the class for each test, so we gain some performance.

But one of the coolest features is that someone has written a gradle plugin
---
@snap[north span-100]
## Gradle plugin
@snapend


- [szpak/gradle-pitest-plugin](https://github.com/szpak/gradle-pitest-plugin)
- `apply plugin: pitest`
- generates `pitest<Variant>` tasks

Note:
100% plug & play

import plugin, set target classes

generate build variant tasks

downside

The gradle plugin for pitest is great, because its almost 100% plug & play. After importing the plugin, and setting up the set of target classes, the plugin will generate pitest tasks for each build variant, which will do _everything_ for you. The only downside about it...

---

@snap[midpoint span-100]
![y-u-do-dis](assets/img/pitest-gradle-plugin-no-android.png)
@snapend

Note:
is that it doesn't work with Android projects on its own. :innocent:
However, there is a fork that does work, so lets take a look at that
---
@snap[north span-100]
## Android Gradle plugin
@snapend

- [koral--/gradle-pitest-plugin](https://github.com/koral--/gradle-pitest-plugin/)
- written by Karol Wrótniak, forked from szpak/gradle-pitest-plugin
- works with Android projects
- has some Android specific helpers (eg: generating mockable Android jar)

Note:
koral forked

extra helpers -> Robolectric

so koral on github has forked and fixed the pitest gradle plugin to work with Android, and added some extra helpers that are useful for Android projects, such as support for Robolectric.
---?code=src/main/kotlin/build.gradle.kts&lang=kotlin@name=build.gradle.kts

@snap[north span-100]
## Android Gradle plugin
@snapend

Note:
installation

plugin

pitest

Robolectric

target classes

output format

Installation is the same as the other gradle plugin, just apply the plugin to your build.gradle file and then declare which classes to test
@[2](apply the plugin)
@[5-9](add the pitest block)
@[6](set this to false if using Robolectric)
@[7](set the target classes)
@[8](set the output format)

---

## Demo

Note:
So, lets see it in action shall we?

Gradle plugin

Run pitest
./gradlew pitestDebug
about 43 seconds

Show output
./openPitest.sh

Explain Green lines - mutation was caught, test failed

Explain Red lines - mutation not caught, tests pass

How can we fix red lines? - write tests

Test - `setSessions should update the controller`()
./gradlew app:clean pitestDebug
about 15 seconds

./openPitest.sh

Here we fixed the red line by adding a test for this line specifically. So what we have done here, is found a section of our app that we were not testing properly, and added a test to cover it.

---

# Pitest tips & tricks

Note:
Finally I'd like to finish on a couple of tips and tricks for getting your pitest setup working nicely
---
@snap[north span-100]
## Pitest kotlin
@snapend


- [pitest/pitest-kotlin](https://github.com/pitest/pitest-kotlin)
- MutationInterceptor
- Removes mutants for Kotlin generated code

Note:
editing bytecode -> implicit support

Kotlin compiler generated code

Intrinsics.checkParameterIsNotNull()

we didn't write, don't want to test

plugin intercepts

First, if you're using kotlin, then the pitest-kotlin plugin is great.
Because PITest works on editing the bytecode rather than the source code, it natively supports both Kotlin & Java which is nice, however any bytecode that is added by the Kotlin compiler, such as `Intrinsics.checkParameterIsNotNull()` which is added when we have a nullable parameter in a function. PITest doesn't know that this is not code we wrote ourselves, and so will try to make a mutant for this code.
We can use the pitest-kotlin plugin to intercept and remove these mutations before they are tested, which will clean up our reports, and speed up the test runtime.

---

## Run PITest on Unit tests only


Note:
Integration test potential variables

connected to DB / API

performance

Integration tests have too many potential variables, + could potentially be connected to DBs etc which will do bad things if you remove certain calls without mocking.
---

## Run PITest on CI

Note:
PITest smart, not fast

monolithic
6min, 10min, git hook

CI

git push

PITest is smart, and will only run tests against mutations that have coverage, however it still isn't the fastest. For example, the monolithic project I'm working on now takes about 6 minutes to run all the tests, and roughly 10 minutes to run pitest. This is a bit too long for my liking to run as a git-hook, so for this project we set up pitest to run on CI when a Pull Request is made. However, if you have a more modularized project, you could run pitest as a git-hook before you push to your remote, to make sure that your tests are covering all possible code paths

---
@snap[north span-100]
## Mutation Testing
@snapend

@snap[east]
![repo-qr](assets/img/mutation_testing_repo_qr.png)
@snapend



- [panpanini/mutation_testing](https://github.com/panpanini/mutation-testing)
- [Github: panpanini](https://www.github.com/panpanini)
- [Twitter: panini_ja](https://www.twitter.com/panini_ja)

Note:
Thanks

sparked interest, stable app

QR -> github + slides

Twitter

Thanks everyone for listening! Hopefully I sparked some interest in mutation testing today, if you would like to look at the slides they're available here at this QR code, or on Github along with the demo project, and if you have any questions you can reach out to me here or on Twitter
