import 'package:appwestu/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';

class AdminCourses extends HookWidget {
  const AdminCourses({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
   return Scaffold(
      appBar: appBarMain(context,'Corsi'),
      body: const Text('CommonBookings'));
  }
}