import 'package:appwestu/components/common/myBookingCard.dart';
import 'package:appwestu/utils/GenericRequests.dart';
import 'package:appwestu/utils/functions.dart';
import 'package:appwestu/widgets/widgets.dart';
import 'package:flutter/material.dart';
import 'package:flutter_hooks/flutter_hooks.dart';
import 'package:scrollable_positioned_list/scrollable_positioned_list.dart';
import 'package:top_snackbar_flutter/custom_snack_bar.dart';
import 'package:top_snackbar_flutter/top_snack_bar.dart';

class MyBookings extends HookWidget {
  const MyBookings({Key? key}) : super(key: key);
  final limit = 10;

  @override
  Widget build(BuildContext context) {
    final currentPage = useState(0);
    final isLoading = useState(false);
    final topContainer = useState(0.0);
    final topContainerIndex = useState(0);
    final itemsData = useState([]);
    ItemScrollController controller = ItemScrollController();
    ItemPositionsListener itemPositionsListener =
        ItemPositionsListener.create();
    final forceRerender = useState(false);
    final Size size = MediaQuery.of(context).size;
    void controllerCallBack() {
      topContainer.value =
          itemPositionsListener.itemPositions.value.first.itemTrailingEdge;
      topContainerIndex.value =
          itemPositionsListener.itemPositions.value.first.index;
    }

    void setPage(value) {

      currentPage.value = value;
    }

    void disdici(idprenotazione,status,index) async {
      var res = await GenericRequests.genericPut(
          '/auth/common/prenotazionieffettuate?idripetizione=$idprenotazione&statuschange=$status'
      );
      
      if (res['status'] == 'fail') {
        if (res['error'] != null && res['error'] == 'LOGINREQUIRED') {
          logoutUserClient(context);
        } else {
          showTopSnackBar(
            context,
            CustomSnackBar.error(
              message: res['message'],
            ),
          );
        }
        if (controller.isAttached) {
          controller.scrollTo(
              index: index,
              duration: const Duration(seconds: 2),
              curve: Curves.easeInOutCubic);
        }
        return;
      }
      showTopSnackBar(
        context,
        CustomSnackBar.success(
          message: res['message'],
        ),
      );
      forceRerender.value = !forceRerender.value;
    }

    final getDataAndRender = useMemoized(
      () => () async {
        List<Widget> listItems = [];
        isLoading.value = true;
        var res = await GenericRequests.genericGet(
            '/auth/common/prenotazionieffettuate?currentpage=${currentPage.value}&limit=$limit');
        isLoading.value = false;

        if (res['status'] == 'fail') {
          if (res['error'] != null && res['error'] == 'LOGINREQUIRED') {
            logoutUserClient(context);
          } else {
            showTopSnackBar(
              context,
              CustomSnackBar.error(
                message: res['message'],
              ),
            );
          }
          return;
        }
        res['data'].asMap().forEach((index, item) {
          listItems.add(MyBookingCard(
              index,
              item['doc']['id'],
              (idprenotazione, status , index) =>
                 disdici(idprenotazione, status , index),
              item['date'],
              item['actualTime'],
              item['doc']['nome'],
              item['doc']['cognome'],
              item['corso']['id'],
              item['corso']['titolo'],
              item['id'],
              item['status'],
              key: UniqueKey()));
        });
        listItems.add(
            pagination(currentPage.value, limit, setPage, listItems.length));
        itemsData.value = listItems;
      },
      [],
    );

    useEffect(() {
      getDataAndRender();
      return null;
    }, [forceRerender.value,currentPage.value]);

    useEffect(() {
      itemPositionsListener.itemPositions.addListener(controllerCallBack);
      return () => itemPositionsListener.itemPositions
          .removeListener(controllerCallBack);
    }, [controller]);
    return SafeArea(
      child: Scaffold(
          appBar: appBarMain(context, 'Le mie prenotazioni'),
          body: SizedBox(
            height: size.height -
                kToolbarHeight - // top AppBar height
                MediaQuery.of(context).padding.top - // top padding
                kBottomNavigationBarHeight,
            child: Column(children: <Widget>[
              Expanded(
                  child: ScrollablePositionedList.builder(
                      itemScrollController: controller,
                      itemCount: itemsData.value.length,
                      itemPositionsListener: itemPositionsListener,
                      physics: const BouncingScrollPhysics(),
                      itemBuilder: (context, index) {
                        double scale = 1.0;
                        if (topContainer.value < 0.30 &&
                            topContainerIndex.value == index) {
                          scale = 0.5 + topContainer.value;
                          if (scale < 0) {
                            scale = 0;
                          } else if (scale > 1) {
                            scale = 1;
                          }
                        }
                        return Opacity(
                          opacity: scale,
                          child: Transform(
                            transform: Matrix4.identity()..scale(scale, scale),
                            alignment: Alignment.bottomCenter,
                            child: Align(
                                heightFactor: 1,
                                alignment: Alignment.center,
                                child: itemsData.value[index]),
                          ),
                        );
                      })),
            ]),
          )),
    );
  }
}
