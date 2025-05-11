# SwiftTest 🍌 — A Lightweight, No-Reflection Alternative to Cucumber

**SwiftTest** is a blazing-fast, minimalistic, and developer-friendly test runner that provides a Cucumber-like experience without the overhead of reflection-heavy frameworks. It uses simple JSON feature definitions and functional interfaces to describe and run scenarios.

> 💡 Ideal for developers frustrated with the sluggishness and complexity of Cucumber and seeking a faster, pluggable, and lightweight alternative.

---

## 🚀 Features

* ✅ Reflection-free method execution
* ✅ JSON-based feature definitions (no Gherkin parser required)
* ✅ Supports `Runnable`, `Consumer`, `BiConsumer`, and `TriConsumer` steps
* ✅ Simple context sharing with type safety
* ✅ Clean and extensible API
* ✅ Tag filtering (partially scaffolded)

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com.sougata.swifttest/
│   │       ├── BananaRunner.java          // Main entry point
│   │       ├── SampleUsage.java           // Sample usage example
│   │       ├── interfaces/                // Custom functional interfaces
│   │       ├── model/                     // Feature, Scenario, Step models
│   │       └── store/                     // MethodStore and TypedContext
│   └── resources/
│       ├── application.properties
│       ├── feature1.json                  // Sample feature definition
│       └── feature2.json
```

---

## 🏁 Getting Started

### 1. Clone & Build

```bash
git clone https://github.com/your-username/swifttest.git
cd swifttest
./mvnw clean install
```

### 2. Define Features (JSON)

Create feature files inside `src/main/resources`:

```json
{
  "name": "Login Feature",
  "tags": ["login"],
  "scenarios": [
    {
      "name": "Valid login",
      "tags": ["happy"],
      "exampled": false,
      "steps": [
        {"name": "openLoginPage", "params": []},
        {"name": "enterCredentials", "params": ["user", "pass"]},
        {"name": "assertLoginSuccess", "params": []}
      ]
    }
  ]
}
```

### 3. Register Steps

In your `SampleUsage.java` or test class:

```java
Map<String, Object> steps = new HashMap<>();
steps.put("openLoginPage", (Runnable) () -> System.out.println("Opening login page..."));
steps.put("enterCredentials", (BiConsumer<String, String>) (u, p) -> System.out.println("User: " + u + ", Pass: " + p));
steps.put("assertLoginSuccess", (Runnable) () -> System.out.println("Login success"));

BananaRunner runner = new BananaRunner();
runner.getMethodStore().registerAll(steps);
runner.run();
```

---

## 📖 Step Interface Support

* `Runnable`: For no-arg steps
* `Consumer<T>`: For single argument steps
* `BiConsumer<T, U>`: For two-argument steps
* `TriConsumer<T, U, V>`: For three-argument steps

All these are custom interfaces defined under `com.sougata.swifttest.interfaces`.

---

## 🔁 Data-Driven Scenarios

Supports examples via `exampled: true` and `examples` list.

```json
{
  "name": "Login with examples",
  "exampled": true,
  "steps": [
    {"name": "enterCredentials", "params": []},
    {"name": "assertLoginSuccess", "params": []}
  ],
  "examples": [
    {"params": [["user1", "pass1"], []]},
    {"params": [["user2", "pass2"], []]}
  ]
}
```

---

## 🧐 Shared Context

`TypedContext` lets you store and retrieve data between steps:

```java
TypedContext ctx = runner.getMethodStore().getSharedContext();
ctx.set("authToken", "abc123");

String token = ctx.get("authToken", String.class);
```

Context is reset between scenarios and examples.

---

## 🐒 BananaRunner

This is the main orchestrator. It:

* Loads all `.json` features in `resources/`
* Executes each scenario and step in order
* Matches step names to registered methods

Usage:

```java
BananaRunner runner = new BananaRunner();
runner.run();
```

Optional tag filtering is partially supported via constructor.

---

## 📌 Why Not Cucumber?

Cucumber is powerful but:

* Relies heavily on reflection (slow startup)
* Uses regex-based matching (brittle)
* Gherkin syntax is verbose and rigid

**SwiftTest** removes this ceremony while preserving intent and structure.

---

## 📦 Roadmap

* [ ] Add JUnit integration
* [ ] Full tag filtering and exclusion
* [ ] HTML/Markdown test reporting
* [ ] Parallel execution support

---

## 🤝 Contributing

Pull requests welcome! Feel free to fork and improve.

---

## 👤 Sample Output

```
=============================================================================
====== Running feature: Login Feature ======
	***** Running scenario: Valid login *****
Opening login page...
User: user, Pass: pass
Login success
```

---

Happy testing! 🚀
