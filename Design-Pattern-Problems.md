# Practice Design Pattern - FAANG

## Practice this before approaching any LLD problems
The sheet below covers 13 patterns and 43 problems. Each problem gives you the requirements, a change request to apply after your first version works, and the follow-up an interviewer is likely to ask. I’ve added the patterns your original list was missing: Command, Template Method, Adapter, Facade, Singleton and Composite.

Rules for every problem:

Write an interface and its implementations, plus a small Main or JUnit test that exercises them.
Use BigDecimal for money.
Do the change request without editing existing classes, only adding new ones. If you can’t, your design leaks.
Time-box each problem to 30–40 minutes.

### Strategy: swap algorithms at runtime

    1. S1. Payment Processing
        - Build PaymentService.pay(BigDecimal amount, PaymentMethod method), returning a PaymentResult (success, transactionId, fee).
        - UPI: validate the VPA format (name@bank), reject amounts over ₹1,00,000, no fee.
        - Credit Card: run a Luhn check on the card number, 2% fee.
        - Net Banking: bank code must be in a supported list, flat ₹10 fee.
        - Change request: Add Wallet payments with 1% cashback.

    Follow-up: Where does strategy selection live? Implement it with a Map<PaymentType, PaymentStrategy> and explain why that’s still better than a switch inside pay().

    S2. Discount Calculator
        - A Cart holds items with price, quantity and category. Implement:
        - Flat: ₹200 off if the cart total is over ₹1,000.
        - Percentage: 10% off, capped at ₹500.
        - Festival: 20% off Electronics items only.
        - The final price can never go below zero.
        - Change request: Allow multiple discounts, applied in two modes: best-of (pick the largest) and stacked (apply in sequence). Do it with a CompositeDiscountStrategy, not by changing the existing ones.
        - Follow-up: Does stacking order change the result? Who decides the order?

    S3. Shipping Cost Calculator
        - Input: a package (weight, dimensions) and a destination (domestic or country code).
        - Standard: ₹50 + ₹10/kg.
        - Express: 2× Standard + ₹100.
        - International: a zone-based rate table plus 18% customs.
        - Use volumetric weight (L×W×H/5000) if it’s larger than the actual weight.
        - Change request: Add a cheapestOption() that evaluates every strategy applicable to the destination.
        - Follow-up: The strategies need different inputs. How do you design the method signature without bloating it? (Hint: a context or request object.)
        
    S4. File Compression
        - Compressor.compress(InputStream, OutputStream) and decompress(...). Implement ZIP and GZIP with java.util.zip, and RAR as a stub that throws UnsupportedOperationException.
        - Change request: Choose the strategy automatically: no compression under 1 KB, GZIP for single files, ZIP for multiple files.
        - Follow-up: Why streams instead of byte[]? What happens with a 5 GB file?
        
    S5. Route Navigation
        - Given a graph of cities with edges (distance, time, hasToll), return a path using:
        - Fastest (minimise time)
        - Shortest (minimise distance)
        - Toll-free (exclude toll edges, then shortest)
        - Change request: Add “Scenic” (prefer edges tagged scenic).
        - Follow-up: Notice that Dijkstra stays the same and only the edge weighting changes. Could the strategy be a Function<Edge, Double> lambda instead of a class hierarchy? When is a full class better?
        
    S6. Sorting Engine
        - Sorter<T> takes a SortStrategy<T> (Bubble, Quick, Merge) and a Comparator<T>.
        - Change request: Auto-select Insertion sort for n < 20, Merge sort when stability is required, Quick sort otherwise.
        - Follow-up: Comparator is itself a Strategy in the JDK. Find two other Strategy examples in the JDK or Spring.
    
### Factory (Simple Factory, Factory Method, Abstract Factory)
        
    F1. Notification Factory
        - NotificationFactory.create(Channel) returns an Email, SMS or WhatsApp notifier. Each one validates its recipient differently (email regex, 10-digit phone, phone with country code).
        - Change request: Add Push notifications without modifying the factory. Use a registry: register(Channel, Supplier<Notifier>).
        - Follow-up: Each notifier needs config (API keys, SMTP host). Who injects it? How does Spring DI make this factory partly redundant?
        
    F2. Payment Gateway Factory
        - Select a gateway by currency and country: Razorpay for INR, Stripe for USD/EUR/GBP, PayPal as the fallback.
        - Change request: Per-merchant override (merchant X always uses Stripe).
        Follow-up: Gateways are expensive to build (HTTP clients). Should the factory cache instances? Is that still a factory?
        
    F3. Document Parser (Factory Method)
        - Abstract DocumentProcessor with process(file), which calls an abstract createParser(). Subclasses: PdfProcessor, DocxProcessor, CsvProcessor. Each parser returns Document(text, metadata).
        - Change request: Detect the type by magic bytes instead of extension (%PDF, PK for DOCX).
        Follow-up: Explain the difference between Simple Factory and Factory Method using your own code. What happens with an unknown type: exception, null, or a NullParser?
        
    F4. Vehicle Factory (Abstract Factory)
        - VehicleFactory has createCar(), createBike() and createTruck(). Two families: ElectricVehicleFactory (battery range) and PetrolVehicleFactory (tank capacity).
        - Change request: Add a Hybrid family.
        Follow-up: Adding a new product (Bus) forces changes to every factory. That’s the known weakness of Abstract Factory. How would you mitigate it?
        
    F5. Logger Factory
        - LoggerFactory.getLogger(String name) returns a Console logger in dev, and File + Database loggers in prod, based on a config property. The same name returns the same instance.
        Follow-up: This is how SLF4J works. Is it thread-safe? Use ConcurrentHashMap.computeIfAbsent.

### Observer: one-to-many notification
        
    O1. Stock Price Alert
        - StockTicker publishes (symbol, price). Subscribers register with rules: “alert if INFY > ₹1,800” or “alert if it drops more than 5% from the previous price”. They can unsubscribe.
        - Change request: Make notification asynchronous through an ExecutorService.
        Follow-up: An observer unsubscribes inside its own callback. Does your code throw ConcurrentModificationException? Fix it with CopyOnWriteArrayList. Price updates P1 and P2 are sent async: can an observer see P2 before P1?
        
    O2. YouTube Subscription
        - Channel.upload(video) notifies subscribers. Each subscriber chooses a bell setting: ALL, PERSONALISED (only videos tagged with their interests) or NONE.
        Follow-up: A channel has 50 million subscribers. Why does an in-process observer break down, and what replaces it (Kafka-style pub/sub with fan-out workers)? This connects LLD to system design.
        
    O3. Order Status Notification
        - When Order.setStatus() is called, notify CustomerEmailer, WarehouseService and AnalyticsTracker.
        - Change request: AnalyticsTracker throws an exception. The other observers must still be notified, and the failure must be logged.
        Follow-up: Should the order status change roll back if notification fails? Introduce an outbox or async event, and explain why.
        
    O4. Weather Station (push vs pull)
        WeatherStation has temperature, humidity and pressure. Displays:
        
        CurrentDisplay
        StatisticsDisplay: min, max and running average
        ForecastDisplay: “rain likely” if pressure is dropping
        Implement it twice: push (the station sends all the data) and pull (the station sends this, and displays call getters).
        Follow-up: Trade-offs between push and pull? Which one survives adding a new field without breaking observers?
        
    O5. Generic Event Bus
        EventBus.subscribe(Class<E> type, Consumer<E> handler) returns a Subscription handle with unsubscribe(). publish(event) delivers only to handlers of that type.
        
        Change request: Add subscriber priority (higher-priority handlers run first).
        Follow-up: What is the lapsed-listener memory leak? Would WeakReference help, and what’s the catch?

### Decorator: add behaviour by wrapping
        
    D1. Coffee Ordering
        Beverage has cost() and description(). Bases: Espresso ₹100, Latte ₹130. Add-ons: Milk +₹20, Chocolate +₹30, Whipped Cream +₹25.
        
        Change request: Support double milk, and a size (S/M/L) that scales add-on prices.
        Follow-up: Count the subclasses you’d need with pure inheritance for 2 bases × 3 add-ons with repeats. That’s the class explosion Decorator avoids.
        
    D2. Logger Enhancement
        Logger.log(String msg). Decorators: TimestampLogger, EncryptionLogger (Base64 is fine), CompressionLogger.
        
        Follow-up: Compress then encrypt, or encrypt then compress? Why does order matter? Encrypted data doesn’t compress.
        
    D3. HTTP Client Wrappers
        HttpClient.execute(Request) returns a Response. Decorators:
        
        AuthClient: adds a Bearer header
        RetryClient: 3 retries with exponential backoff, only on 5xx
        LoggingClient: logs method, URL and latency
        CachingClient: caches GET requests for 60 seconds
        Follow-up: Where should Retry sit relative to Logging and Auth, and why? How does this compare to OkHttp interceptors or servlet filters?
        
    D4. Text Formatting
        Text.render(), with decorators that produce <b>, <i>, <u>.
        
        Change request: Remove italic from an already decorated object.
        Follow-up: Why is unwrapping awkward? How do decorators affect equals() and instanceof checks?
        
### State: behaviour depends on current state
        
    ST1. Document Workflow
        States: Draft → Review → Published. Actions: edit(), submit(), approve(), reject().
        
        Only an Admin can approve.
        Reject sends the document back to Draft.
        Published documents can’t be edited.
        Change request: Add an Archived state reachable from Published.
        Follow-up: Should states know about user roles, or should the context check them?
        
    ST2. Order Lifecycle
        Created → Paid → Shipped → Delivered, plus Cancelled.
        
        Cancel is allowed only before Shipped.
        Cancelling after Paid triggers a refund.
        Illegal transitions throw IllegalStateException.
        Follow-up: The order is stored in a database. How do you persist the state (an enum column) and rehydrate the state object? Rewrite it as an enum with a transition table: when is that simpler than full State classes?
        
    ST3. Vending Machine
        Operations: insertCoin(amount), selectProduct(code), dispense(), refund(). States: Idle, HasMoney, Dispensing, OutOfStock.
        
        Return change.
        Reject products that cost more than the inserted money.
        Go to OutOfStock when inventory is empty.
        Follow-up: Two users press buttons at once. What do you synchronise?
        
    ST4. Media Player
        States: Playing, Paused, Stopped. Buttons play(), pause() and stop() behave differently in each state (for example, pause while Stopped does nothing).
        
        Follow-up: Are your state objects stateless? If so, share them as singletons.

### Chain of Responsibility: pass a request along handlers
        
    C1. ATM Cash Withdrawal
        Dispensers for ₹2000, ₹500, ₹200 and ₹100, each holding a limited number of notes.
        
        Reject amounts that aren’t a multiple of 100.
        If the full amount can’t be dispensed, dispense nothing (roll back).
        Follow-up: Greedy fails with limited notes. Example: ₹600 with one ₹500 note, three ₹200 notes and no ₹100 notes. Greedy takes the ₹500 and gets stuck, but 3×₹200 works. How would you fix it, and is it still CoR?
        
    C2. Request Validation Pipeline
        Handlers: Authentication (valid token) → Authorization (role) → Validation (required fields). Any handler can short-circuit with an error response.
        
        Change request: Insert a RateLimiter between Authentication and Authorization without editing any handler. Build the chain with a builder.
        Follow-up: This is pass-through-until-failure (like servlet filters). Contrast it with first-match-wins (the ATM).
        
    C3. Logging Framework
        INFO, DEBUG and ERROR handlers. Each handles messages at or above its level and passes them on.
        
        Follow-up: What happens if the chain is misordered, or no handler handles a message? Add a default terminal handler.
        
    C4. Expense Approval
        Manager approves up to ₹10,000, Director up to ₹1,00,000, VP up to ₹10,00,000. Anything above is rejected.
        
        Change request: Load the limits from config, and skip an approver who is on leave.
        Follow-up: Return the full approval trail (who saw it and what they decided).

### Command: requests as objects (undo, queueing)
        
    CM1. Text Editor Undo/Redo
        Commands: InsertText(pos, text), DeleteText(pos, len), Bold(range). Implement undo() and redo() with two stacks.
        
        Follow-up: Why must the redo stack clear when a new command runs? Compare storing inverse operations with storing snapshots (Memento) in terms of memory.
        
    CM2. Smart Home Remote
        Slots bind to commands: LightOn, FanSpeedUp, ACSetTemp(22). Add a MacroCommand (“Party mode” runs several commands), with undo for the macro.
        
    CM3. Job Queue
        Commands are submitted to a queue and executed by N worker threads. Failed commands retry up to 3 times, then go to a dead-letter list.
        
        Follow-up: Runnable and Callable are Commands. What does a command need for retries to be safe? (Idempotency.)
        
### Template Method: fixed skeleton, variable steps
        
    T1. Data Export
        export() is final: fetchData → transform → format → write. Subclasses: CSV, JSON and PDF exporters. Add a hook includeHeader() that defaults to true.
        
        Follow-up: Rebuild it with Strategy (inject a Formatter). Which one do you prefer, and why? Inheritance couples subclasses to the base class.
        
    T2. Order Processing
        validate → reserveInventory → charge → notify. OnlineOrder charges a card and emails the customer. InStoreOrder charges cash and prints a receipt.
        

### Adapter: make incompatible interfaces fit
        
    A1. Payment Gateway Adapter
        Your interface is PaymentProcessor.charge(BigDecimal rupees, String customerId).
        
        The “Stripe SDK” (write a fake one) takes createCharge(long cents, String currency, String source).
        The fake “Razorpay SDK” needs createOrder(paise) first, then capture(orderId).
        Write adapters for both.
        Follow-up: Where should currency conversion live? How do you map each SDK’s exceptions into your own exception hierarchy?
        
    A2. Legacy Weather API
        A legacy API returns Fahrenheit as XML strings. Your app expects a WeatherData object in Celsius. Write an adapter.
        
### Facade: one simple entry point to a subsystem
        
    FA1. Order Placement
        OrderFacade.placeOrder(cart, user) coordinates InventoryService, PaymentService, ShippingService and NotificationService.
        
        Change request: If payment fails, release the reserved inventory.
        Follow-up: At what point does a facade become an orchestrator or saga? Is a facade with business logic a smell?
        
    FA2. Home Theatre
        watchMovie() dims the lights, turns on the projector, sets the input and starts the player. endMovie() reverses all of it.
        
### Singleton (know it, then critique it)
        
        SG1. Configuration Manager
        Implement it five ways: eager, synchronized lazy, double-checked locking (explain why volatile is required), Bill Pugh holder, and enum.
        
        Break the non-enum versions with reflection and with serialization, then fix them.
        Follow-up: Why is Singleton often considered an anti-pattern (hidden global state, hard to mock)? How does a Spring singleton bean differ from a GoF Singleton?
        
        SG2. Unique ID Generator
        Thread-safe and monotonic. Test it with 100 threads generating 10,000 IDs each, with no duplicates.
        
        Follow-up: AtomicLong vs synchronized. Why does this break across multiple JVMs, and how does Snowflake solve it?

### Composite: treat a single object and a group uniformly
        
        CP1. File System
        FileSystemNode has getSize(), print(indent) and find(name). File is a leaf. Directory holds children and its size is recursive.
        
        Change request: Add delete() and a node count.
        Follow-up: Symlinks can create cycles. How do you detect them? Should leaves throw on add(), or should add() live only on Directory? (Safety vs transparency.)
        
        CP2. Org Chart
        Employee and Manager (who has reports). Compute total salary cost and headcount under any node.

### Builder and Proxy
        
    B1. Complex User Object
        User has required fields (id, email) and about 8 optional ones (phone, address, preferences…). Make it immutable, validate the email and an age of at least 18 in build(), and keep collections defensively copied.
        
        First write the telescoping-constructor version and feel the pain.
        Follow-up: Lombok @Builder vs hand-written. What does a “step builder” enforce at compile time?
        
    B2. SQL Query Builder
        Fluent API: select("name").from("users").where("age > 18").orderBy("name").limit(10).build(). It must be impossible to call where() before from() (use step interfaces).
        
    P1. Access-Controlled Document Service
        DocumentService has read(id) and write(id, content). The proxy checks the user’s role (Viewer can read; Editor can read and write) and writes an audit log entry for every call.
        
    P2. Caching Proxy
        Wrap a slow ExchangeRateService (sleep 2 seconds) with a proxy that caches results for 60 seconds.
        
        Follow-up: Two threads miss the cache at the same moment. How do you avoid a cache stampede?
        
    P3. Lazy-Loading Proxy
        HighResImage loads from disk only on the first display() call.
        
        Follow-up: Proxy vs Decorator: the structure is identical, so what’s the difference? (Intent: controlling access vs adding behaviour.) Why does @Transactional not work when a Spring bean calls its own method?
        Done when

For each pattern, you can:

Code it from scratch in under 15 minutes.
Explain the if-else version it replaces.
Name one JDK or Spring example.
Answer the follow-ups above without notes.
Where to practise
Refactoring.Guru – Java patterns: check your solution against the canonical structure after each problem.
awesome-low-level-design (GitHub): pattern-wise Java examples to compare against your own.
LeetCode 1188 – Bounded Blocking Queue: for the concurrency follow-ups in Observer, Singleton and Command.