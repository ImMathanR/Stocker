## Design Decisions
### Patterns Used
* Facade Pattern for **Stocker** class
* Builder Pattern for Building the Stocker class
* Dependency Injection across the classes. That makes sure all the Dependencies of a class will not create it's dependencies but accepts it from the construction. This improves testability.
### Librarites Used
* OkHttp - To download the assets
* kotlin-coroutines - For a simplified async interface.