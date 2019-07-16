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
Like many of you here today, my job as a client engineer is about providing value to our customers, by solving problems. Our main tools for solving these problems are shipping features, and improving existing functionality. But the focus here is on shipping.

---

@snap[north]
## My job
@snapend

- A non-shipped feature doesn't provide benefit
- Ship features as quick as possible

Note:
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
If we ship a feature that is buggy, or broken in some way, then this also doesn't provide benefit, and in extreme cases can actually reduce the benefit of the product. So really, we should be focused on shipping _quality_ features as quick as possible.

---

@snap[north span-100]
## Maintaining quality
@snapend

- QA |
- Code Review |
- Tests |

Note:
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

Note:
Alright, lets start mutating some code! Take this class as an example. There are 3 functions here:

@[5-9](setSessions)
@[6-7](clear sessions, add all new)
@[8](request that the current models be built)
@[11-15](buildModels - generates the models, adds to the controller)
@[13](calls generate models)
@[14](adds each one to the Controller)
@[17-33](generateModels)
@[19](Takes the list of sessions)
@[21](Creates a SessionModelInstance)
@[22-30](Sets the session data to the model)

---

@snap[north span-100]
## Creating mutations
@snapend

 - Competent Programmer Hypothesis |
 - Coupling Effect |

Note:
Now we could go randomly changing code to create mutants, but remember that for each mutant we create, we have to run all the tests against it to make sure it is killed, so we want to be methodical about the changes that we make.

This is actually a fairly well researched area, so if you Google you can find lists of mutators for object-oriented languages, however the basic idea behind creating mutations follows these two theories.

The competent Programmer Hypothesis states that bugs introduced by competent programmers are more often than not small syntatical errors, which are often hard to notice during code review.

Secondly, the Coupling effect states that small issues will stack up to create big issues, so if we can reduce small issues then we can also reduce the big issues at the same time.

With that in mind, lets take a look at a couple of these as an example, arranged in order of perceived evilness, which is a metric I made up based on how difficult I think these types of faults are to find.

---
@snap[north span-100]
## Conditionals boundary
@snapend

replaces relational operators with boundary counterpart

| Original | Mutated |
|-|-|
| < | <= |
| <= | < |
| > | >= |
| >= | > |

Note:
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
So if we apply this to our previous code example, we come up with a mutant that looks like this. Now - thats pretty evil, this is definitely the kind of thing that could slip through code review, and this kind of edge case is unlikely to be covered by tests, unless you have gone out of your way to write a test specifically for this boundary.

Our next contender is slightly more evil, the math operator
---
@snap[north span-100]
## Math
@snapend


replaces binary arithmetic operations

| Original | Mutated |
|-|-|
| + | - |
| - | + |
| * | / |
| / | * |

Note:
This lil critter will take your nicely crafted binary arithmetic and swap it all around. for example
---?code=src/main/kotlin/math.kt&lang=kotlin
@snap[north span-100]
## Math
@snapend

Note:
did you notice the change?
This one character change can easily lead to an `IndexOutOfBoundsException`

---
@snap[north span-100]
## Negate Conditionals
@snapend


replaces conditional checks

| Original | Mutated |
|-|-|
|==|!=|
| != | == |
| <= | > |
| > | <= |

Note:
Next we have the negate conditionals operator - are you seeing a pattern here? Here we take conditional checks, and reverse them.
---
@snap[north span-100]
## Negate Conditionals
@snapend

``` kotlin
// original
fun buildModels() {
    ProgressModelView_()
      .title(title)
      ...
      .addIf(model.state == State.IN_PROGRESS)
}

// mutated
fun buildModels() {
    ProgressModelView_()
      .title(title)
      ...
      .addIf(model.state != State.IN_PROGRESS)
}
```

Note:
This one is pretty evil too, as even just this change on its own can lead to wildly different behaviour of our app, in this case showing the Progress view whenever the state is _not_ in progress.

---
@snap[north span-100]
## Remove void calls @emoji[smiling_imp]
@snapend

*removes* void method calls

Note:
The last mutator I want to show is the Remove void calls operator, this one straight up removes calls to void methods. just deletes them. gone.

---
@snap[north span-100]
## Remove void calls @emoji[smiling_imp]
@snapend

``` kotlin
// original
fun onNext(items: List<Item>) {
    controller.items = items
    controller.requestModelBuild()
}

// mutated
fun onNext(items: List<Item>) {
    controller.items = items

}
```
Note:
Back to our code from before, and bam, we're no longer building the models in our EpoxyController, so now our RecyclerView never gets updated.
This might sound like not that big of a deal, I think more often than not we forget to test void calls in functions, as to do so you have to properly set up mocks and actively call them.

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
So, for our short snippet of code, we already have 4 mutations that we need to run the test suite against. If our test suite takes 5 minutes per run which is a pretty conservative estimate, then with just these 4 mutations we already have to spend 20 minutes testing these mutations, which isn't really realistic.

instead, if we use code coverage to find out which tests are actually calling this section of code, we can restrict our test runs to only those specific tests, which greatly reduces the number of tests we have to run, and so also reduces the time spent testing mutations to a more reasonable level.

---
@snap[north span-100]
## So what?
@snapend

Note:
We have seen how to create mutations from our code, but what does it really mean? Obviously we're not just going to delete lines from our code, so how does this help us? Lets take a quick look at an example.

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
So hopefully from this small demo, you've seen why code coverage alone is not enough. Even though we had 100% code coverage, when we mutated our code, we saw that our coverage didn't change, and our tests still passed. What this tells us is that our test suite in its current state is missing test cases, so changes could sneak into our codebase, and our tests wouldn't warn us about them. Finding these un-checked code paths is what mutation testing is so useful for.

---
## That's a lot of work you expect us to do there bud

Note:
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
PItest is a mutation testing system for the JVM, and it has some pretty sweet features. It will automatically generate mutations based on the operator set provided. Also, the mutations are generated by editing the bytecode of a class after it has been loaded by the ClassLoader, which means that
a) mutations are never saved to disk
b) we don't have to pay the cost of loading the class for each test, so we gain some performance.

But one of the coolest features is that someone has written a gradle plugin, so we can integrate it directly into our Android app
---
@snap[north span-100]
## Gradle plugin
@snapend


- [szpak/gradle-pitest-plugin](https://github.com/szpak/gradle-pitest-plugin)
- `apply plugin: pitest`
- generates `pitest<Variant>` tasks

Note:
The gradle plugin for pitest is great, because its almost plug & play. After importing the plugin, and setting up the set of target classes, the plugin will generate pitest tasks for each build variant, which will do _everything_ for you. The only downside about it...

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
so koral on github has forked and fixed the pitest gradle plugin to work with Android, and added some extra helpers that are useful for Android projects, such as support for Robolectric. Installation is the same as the other gradle plugin, just apply the plugin to your build.gradle file and then declare which classes to test
---?code=src/main/kotlin/build.gradle.kts&lang=kotlin@name=build.gradle.kts

@snap[north span-100]
## Android Gradle plugin
@snapend

Note:
@[8](apply the plugin)
@[11-15](add the pitest block)
@[12](set this to false if using Robolectric)
@[13](set the target classes)
@[14](set the output format)

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
First, if you're using kotlin, then the pitest-kotlin plugin is great.
Because PITest works on editing the bytecode rather than the source code, it natively supports both Kotlin & Java which is nice, however any bytecode that is added by the Kotlin compiler, such as `Intrinsics.checkParameterIsNotNull()` which is added when we have a nullable parameter in a function. PITest doesn't know that this is not code we wrote ourselves, and so will try to make a mutant for this code.
We can use the pitest-kotlin plugin to intercept and remove these mutations before they are tested, which will clean up our reports, and speed up the test runtime.

---

## Run PITest on Unit tests only


Note:
Integration tests have too many potential variables, + could potentially be connected to DBs etc which will do bad things if you remove certain calls without mocking.
---

## Run PITest on CI

Note:
PITest is smart, and will only run tests against mutations that have coverage, however it still isn't the fastest. For example, the monolithic project I'm working on now takes about 6 minutes to run all the tests, and roughly 10 minutes to run pitest. This is a bit too long for my liking to run as a git-hook, so for this project we set up pitest to run on CI when a Pull Request is made. However, if you have a more modularized project, you could run pitest as a git-hook before you push to your remote, to make sure that your tests are covering all possible code paths

---
@snap[north span-100]
## Takeaways
@snapend


- Our job is to ship *quality* features, fast
- Mutation testing helps us ensure *quality*
- PITest helps us do that *fast*

Note:
fix broken tests with mutation testing
