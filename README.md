# Android SDK for ikatago

## Example

Example for using this ikatagosdk is in `Example` folder. Sample codes are in `MainActivity.java` classes.

## Requirements

## Installation

Import the `ikatagosdk.aar` in this folder to your project. See how: 

[http://docs.onemobilesdk.aol.com/android-ad-sdk/adding-aar-files.html](http://docs.onemobilesdk.aol.com/android-ad-sdk/adding-aar-files.html)


## Usage
View the example for detail, in the `Example/app/src/main/java/com/ikatago/kinfkong/ikatagosdkdemo/MainActivity.java`

### 1. Creates a ikatago client and query the server info
```java
    import ikatagosdk.Client;
    import ikatagosdk.DataCallback;
    import ikatagosdk.KatagoRunner;

    final Client client = new Client("", "aistudio", "kinfkong", "12345678");
    // query the server info
    String serverInfo = client.queryServer();
    // you can parse the json string to object
```
Example of the json response of the server info: 
```json
{"serverVersion":"1.4.0","supportKataWeights":[{"name":"20b","description":null},{"name":"30b","description":null},{"name":"40b","description":null},{"name":"40b-large","description":null}],"supportKataNames":[{"name":"katago-1.5.0","description":null},{"name":"katago-1.6.0","description":null},{"name":"katago-1.3.4","description":null},{"name":"katago-solve","description":null}],"supportKataConfigs":[{"name":"default_gtp","description":null},{"name":"10spermove","description":null},{"name":"2stones_handicap","description":null},{"name":"3stones_handicap","description":null},{"name":"4stones_handicap","description":null},{"name":"5stones_handicap","description":null},{"name":"6stones_handicap","description":null},{"name":"7+stones_handicap","description":null}],"defaultKataName":"katago-1.6.0","defaultKataWeight":"40b","defaultKataConfig":"default_gtp"}
```
** Note: `queryServer` api only supports the ikatago-server since 1.4.0 **

### 2. Configs the katago (weights, configs, etc.) and run the katago
```java
     // create the katago runner
    KatagoRunner katago = client.createKatagoRunner();
    // set the katago weight or others
    katago.setKataWeight("20b");
    // start to run the katago
    katago.run(callback);
```

### 3. sends the gtp commands to the katago
```java
    // send GTP command
    katago.sendGTPCommand("version\n");
    katago.sendGTPCommand("kata-analyze B 50\n");
```

### 4. example implementation of the data callback
```java
class DataCallbackImpl implements DataCallback {
    @Override
    public void callback(byte[] bytes) {
        // simply write the data to log
        if (bytes == null) {
            return;
        }
        Log.d("GTP OUTPUT: ", new String(bytes));
    }

    @Override
    public void stderrCallback(byte[] bytes) {
        if (bytes == null) {
            return;
        }
        Log.d("GTP STDERR OUTPUT: ", new String(bytes));
    }

}
```
## Author

kinfkong, kinfkong@126.com

QQ Group: 703409387

## License

IkatagoSDK is available under the MIT license. See the LICENSE file for more info.
