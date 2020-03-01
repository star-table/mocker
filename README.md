# Introduce
A simple mock server. Only swagger is currently supported : ) !
# Install
```powershell
git clone https://github.com/ainilili/mocker.git
cd mocker
mvn clean install
```
# Start-Up
```powershell
java -jar target/mocker.jar http://host:port/swagger/v2/api-docs
```
# Usage
## Get apis
```powershell
curl http://127.0.0.1:10088/m/apis
```
## Do request
Optional parameters:
 - **_listSize**:  list size, default 20.
 - **_mapSize**:  map size, default 20.
 - **_dateFormat**:  date format, default 'yyyy-MM-dd HH:mm:ss'. If you want a timestamp, please enter 'timestamp'.

Sample request:
```powershell
curl http://127.0.0.1:10088/hello?_listSize=5&_mapSize=8&_dateFormat=timestamp
```