import 'package:appwestu/pages/auth/UserLoggedInState.dart';
import 'package:provider/provider.dart';
import 'package:flutter/material.dart';

void logoutUserClient(BuildContext context) async {
      await UserLoggedInState.removeUserRoleAsLogged();
      context.read<UserLoggedInState>().setRole = -1;
}