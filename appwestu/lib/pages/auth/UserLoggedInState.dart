import 'package:flutter/widgets.dart';
import 'package:shared_preferences/shared_preferences.dart';

class UserLoggedInState extends ChangeNotifier {
  String _email = "";
  String _password = "";

  int _role = -1;

  @override
  String toString() =>
      'UserLoggedInState(_isLoggedIn: _role: $_role)';


  int get getRole => _role;

  set setRole(int role) {
    _role = role;
    notifyListeners();
  }
  static Future<void> saveUserRoleAsLogged(int role) async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    await preferences.setInt(
      'role',role);
  }
  static Future<int?> getUserRoleAsLogged() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    return preferences.getInt('role');
  }
  static Future<void> removeUserRoleAsLogged() async {
    SharedPreferences preferences = await SharedPreferences.getInstance();
    await preferences.remove('role');
  }
  String get getEmail => _email;

  set setEmail(String email) => _email = email;

  String get getPassword => _password;

  set setPassword(password) => _password = password;

  
}
