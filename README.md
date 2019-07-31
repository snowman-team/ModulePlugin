Snowball Android Toolbox
============

A simple tool for Android modular communication and page navigating.

## Installation

```groovy
repositories {
    maven { url "https://xueqiumobile.bintray.com/maven" }
}
dependencies {
    // add dependency, please replace x.y.z to the latest version
    implementation "com.xueqiu.moduleplugin:plugin:x.y.z"
    // if you need page navigation
    implementation "com.xueqiu.moduleplugin:router:x.y.z"
}
```

## Usage

### ModulePlugin

Create a method provider interface in a public module.
```kotlin

interface IAppMethodProvider : BaseMethodProvider {
    fun test() // the methods you want to provide for other modules
}
```

Implement methods in your module.
```kotlin

class AppMethodProvider : IAppMethodProvider {
    override fun test() {
        Log.e("method provider", "test")
    }
}
```

Then create your module plugin class.
```kotlin

@ModulePlugin(name = "app")
class AppModulePlugin : BaseModulePlugin() {

    override fun init() {
        // do something you need
    }

    override fun getMethodProvider(): BaseMethodProvider? = AppMethodProvider() // replace to your module method provider

}

```

And use it like below.
```kotlin

ModulePluginManager.init(AppModulePlugin()) // put it to the right place

val provider = ModulePluginManager.getMethodProvider<IAppMethodProvider>("app")
provider?.test()
```

### Router

If you need page navigating between modules, add @RoutePage to your activity.

```kotlin

@RoutePage("^/test$")
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}
```

Register it in your module plugin.
```kotlin

override fun init() {
    RouterManager.register(Route(TestActivity::class.java))
    // do something you need
}

```

Or only register an action.
```kotlin

RouterManager.register(Route("^/test/manual$", object : Route.Action {
    override fun run(ctx: Context, intent: Intent) {
        intent.putExtra("test key", "test value")
        ctx.startActivity(intent)
    }
}))
```

Then use it everywhere.
```kotlin

RouterManager.open(this, "/test") // replace to your url

```

And you can use router manager to handle app link.
```kotlin

RouterManager.handleAppLink(this) { appLink ->
    return@handleAppLink appLink.startsWith("xueqiu") && !appLink.contains("https") // replace this line to your rules
}
```
Test it: adb shell am start -a android.intent.action.VIEW -d "[scheme://appLink]" [app package name]