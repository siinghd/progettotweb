import 'package:appwestu/pages/auth/login.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';

class AuthenticateMainWidget extends HookWidget {
  const AuthenticateMainWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final toggleView = useState(true);
    void toggleViewFunction() {
    toggleView.value = !toggleView.value;
    }
    return toggleView.value
        ? SignInPage(toggleViewFunction)
        : const Center(child: Text('hi'));
  }
}
