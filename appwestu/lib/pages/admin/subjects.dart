
import 'package:appwestu/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';

class AdminSubjects extends HookWidget {
  const AdminSubjects({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
   return Scaffold(
      appBar: appBarMain(context,'Materie'),
      body: const Text('CommonBookings'));
  }
}