import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:requests/requests.dart';

class GenericRequests {
  static Future genericPost(url, dataString) async {
    try {
      var r = await Requests.post(dotenv.env['BACKENDURL']! + url,
          body: dataString, bodyEncoding: RequestBodyEncoding.FormURLEncoded);
     
      dynamic json = r.json();
      return json;
    } catch (e) {
      return {"status": "fail", "message": "Server error"};
    }
  }

  static Future genericGet(url) async {
    try {
      var r = await Requests.get(dotenv.env['BACKENDURL']! + url);
      dynamic json = r.json();
      return json;
    } catch (e) {
      return {"status": "fail", "message": "Server error"};
    }
  }

  static Future genericDelete(url) async {
    try {
      var r = await Requests.delete(
        dotenv.env['BACKENDURL']! + url,
      );
      dynamic json = r.json();
      return json;
    } catch (e) {
      return {"status": "fail", "message": "Server error"};
    }
  }

  static Future genericPut(url) async {
    try {
      var r = await Requests.put(
        dotenv.env['BACKENDURL']! + url,
      );
      dynamic json = r.json();
      return json;
    } catch (e) {
      return {"status": "fail", "message": "Server error"};
    }
  }
}
