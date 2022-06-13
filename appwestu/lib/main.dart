import 'package:appwestu/layouts/adminlayout.dart';
import 'package:appwestu/layouts/userlayout.dart';
import 'package:appwestu/pages/auth/UserLoggedInState.dart';
import 'package:appwestu/pages/auth/authenticateMain.dart';
import 'package:appwestu/pages/common/bookings.dart';
import 'package:appwestu/pages/common/mybookings.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:go_router/go_router.dart';
import 'package:provider/provider.dart';

void main() async {
  await dotenv.load(fileName: ".env");
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  MyApp({Key? key}) : super(key: key);

  final userLoggedInState = UserLoggedInState();

  late final _router = GoRouter(
      redirect: (GoRouterState state) {
        final role = userLoggedInState.getRole;
        final loggingIn = state.subloc == '/';
        if (role == -1) {
          return loggingIn || state.subloc == '/common/bookings' ? null : '/';
        }

        if (role != -1) {
          if ((role == 1 || role == 2) && state.subloc.contains('common')) {
            return null;
          } else if (role == 1 && state.subloc.contains('user')) {
            return null;
          } else if (role == 2 && state.subloc.contains('admin')) {
            return null;
          } else if (role == 1) {
            return '/user';
          } else {
            return '/admin';
          }
        }
        return null;
      },
      refreshListenable: userLoggedInState,
      routes: [
        GoRoute(
            path: '/', builder: (context, state) => const AuthenticateMainWidget()),
        GoRoute(
            path: '/admin', builder: (context, state) => const AdminLayout()),
        GoRoute(path: '/user', builder: (context, state) => const UserLayout()),
        GoRoute(
            path: '/common/bookings',
            name: 'bookingsAvailable',
            builder: (context, state) => const CommonBookings()),
        GoRoute(
            path: '/common/mybookings',
            builder: (context, state) => const MyBookings()),
      ]);

  @override
  Widget build(BuildContext context) =>
      ChangeNotifierProvider<UserLoggedInState>.value(
          value: userLoggedInState,
          child: MaterialApp.router(
            routeInformationParser: _router.routeInformationParser,
            routerDelegate: _router.routerDelegate,
          ));
}
