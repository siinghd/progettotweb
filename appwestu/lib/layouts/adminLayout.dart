import 'package:appwestu/pages/common/bookings.dart';
import 'package:appwestu/pages/common/mybookings.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';

class AdminLayout extends HookWidget {
  const AdminLayout({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final currentIndex = useState(0);
    final screens = [
      const CommonBookings(),
      const MyBookings(),
      /* const AdminAllBookings(),
      const AdminProfessors(),
      const AdminSubjects(),
      const AdminCourses(), */
    ];
    return Scaffold(
      body:  screens[currentIndex.value] ,// or to maintain the state IndexedStack(children: screens,index: currentIndex.value)
      bottomNavigationBar: (BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        backgroundColor: Colors.blue,
        selectedIconTheme: const IconThemeData(size: 30),
        selectedItemColor: Colors.white,
        unselectedItemColor: Colors.white54,
        currentIndex:currentIndex.value,
        showUnselectedLabels: false,
        showSelectedLabels: false,
        onTap: (index) => currentIndex.value = index,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.featured_play_list_outlined), label: ''),
          BottomNavigationBarItem(icon: Icon(Icons.checklist_rounded), label: ''),
          /* BottomNavigationBarItem(icon: Icon(Icons.book), label: ''),
          BottomNavigationBarItem(icon: Icon(Icons.person_add), label: ''),
          BottomNavigationBarItem(icon: Icon(Icons.subject), label: ''),
          BottomNavigationBarItem(icon: Icon(Icons.handshake), label: ''), */
        ],
      )),
    );
  }
}
