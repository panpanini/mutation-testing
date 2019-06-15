## Fixing broken robots - Android Mutation Testing
### DroidConVN 2019

---

<p><span class="slide-title">About me</span></p>

<img align="right" width="150" height="150" src="assets/img/panini.png">
- Matthew Vern / Panini
- Twitter [@panini_ja](), [@panini_en]()
- Github panpanini
- Mercari, Inc
- Software Engineer (Android)

---

<p><span class="slide-title">My job</span></p>

- Client Engineer
- Solving problems for our customers
- Shipping features
- Improve existing functionality

Note:
Emphasis on quality

---

<p><span class="slide-title">My job</span></p>

- A non-shipped feature doesn't provide benefit
- Ship features as quick as possible


---

<p><span class="slide-title">My job</span></p>

- A shipped, broken feature doesn't provide benefit
- Ship _quality_ features as quick as possible

---

<p><span class="slide-title">Maintaining quality</span></p>

- QA |
- Code Review |
- Tests |

---

<p><span class="slide-title">Maintaining quality</span></p>

- How do we know our tests are providing quality
    - Use coverage to make sure that our tests are calling production code
    - higher coverage, more insurance that changes introduced will not break existing code
    - higher coverage, more insurance that new code _does what it says on the tin_

---

# Who ~watches~ tests the ~watch~ testmen?

---
