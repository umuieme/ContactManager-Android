# ContactManager-Android
Basic Contact Manager library to read contact from the android device (for now)

In root gradle

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

In app gradle :

	dependencies {
	        compile 'com.github.umuieme:ContactManager-Android:develop-SNAPSHOT'
	}

