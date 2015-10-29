# Gradle Log Plugin

[![Build Status](https://travis-ci.org/boazj/gradle-log-plugin.svg?branch=master)](https://travis-ci.org/boazj/gradle-log-plugin)
[![Coverage Status](https://coveralls.io/repos/boazj/gradle-log-plugin/badge.svg?branch=master&service=github)](https://coveralls.io/github/boazj/gradle-log-plugin?branch=master)

The plugin that will tail your log files as a gradle task.


## Usage

To build the project execute the following command from the clone directory
```shell
./gradlew clean build install
```

To apply the plugin in your build add the following to the build script
```gradle
apply plugin: 'com.boazj.log'

buildscript {
	repositories {
		jcenter()
	}
	dependencies {
		classpath 'com.boazj.gradle:gradle-log-plugin:0.1.0'
	}
}
```

The Log plugin adds a default 'tail' task to your build lifecycle, this task can be configured by the flowing closure
```gradle
tail {

}
```

Configuration samples:
```gradle
tail {
	log = files('/var/log/somelog.log')
}
```

```gradle
tail {
	log = files('/var/log/somelog.log', '/vat/log/otherlog.log')
}
```

To add your custom tail tasks just create a task of type 'TailLogTask'
```gradle
task other_tail(type: com.boazj.gradle.log.tasks.TailLogTask)
```

To exit the ever-running tail task enter one of 'exit', 'q', 'quit' followed by a new line

Property               	   | Description                                            										          | Default Value
-------------          	   | -------------                                          										          | -------------
_FileCollection_ log      | A collection of log files to tail in a single stream                                                     | null
_boolean_ showOnlyNewLines | If the tail should only show new lines or the entire file 										          | true
_boolean_ showColors       | If the tal should show headers in color (either white, red, yellow or green) when showing multiple logs  | true


## Road Map
- [ ] Add tests
- [ ] Add additional log related tasks - delete, roll, create etc.
- [ ] Add support for remote logs
- [ ] Publish to bintray and plugin portal