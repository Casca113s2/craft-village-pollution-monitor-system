import 'package:fl_nynberapp/src/app.dart';
import 'package:fl_nynberapp/src/resources/dialog/loading_dialog.dart';
import 'package:fl_nynberapp/src/resources/dialog/msg_dialog.dart';
import 'package:fl_nynberapp/src/resources/theme/colors/light_colors.dart';
import 'package:flutter/material.dart';

//import 'package:permission_handler/permission_handler.dart';
class EmailForgotPwPage extends StatefulWidget {
  String email;
  String username;
  EmailForgotPwPage(this.username, this.email);
  @override
  _EmailForgotPwPageState createState() =>
      _EmailForgotPwPageState(this.username, this.email);
}

double _height;
double _width;

class _EmailForgotPwPageState extends State<EmailForgotPwPage> {
//   }
  String email;
  String username;
  _EmailForgotPwPageState(this.username, this.email);
  TextEditingController _emailController = new TextEditingController();
  TextEditingController _reEmailController = new TextEditingController();

  String emailFormat = "";
  String starFormat = "";
  @override
  void initState() {
    super.initState();
    print("Name: " + username.toString() + " Email: " + email.toString());
    for (int i = 0; i < email.length - 2; i++) {
      starFormat += '*';
    }
    emailFormat = email[0] + starFormat + email[email.length - 1];
    _emailController.text = emailFormat;
  }

  @override
  Widget build(BuildContext context) {
    _height = MediaQuery.of(context).size.height;
    _width = MediaQuery.of(context).size.width;
    return Scaffold(
      appBar: AppBar(
        backgroundColor: LightColors.kLightYellow3,
        leading: IconButton(
          icon: Icon(
            Icons.chevron_left,
            size: 40,
          ),
          onPressed: () {
            Navigator.maybePop(context);
          },
          color: Colors.green,
        ),
        //iconTheme: IconThemeData(color: Colors.blue),
        elevation: 0,
      ),
      backgroundColor: LightColors.kLightYellow3,
      body: Container(
        padding: EdgeInsets.fromLTRB(30, 0, 30, 0),
        constraints: BoxConstraints.expand(),
        child: SingleChildScrollView(
          //wrap t???t c??? th???ng con th??nh 1 scroll, tr??nh thi???t b??? b??? nh???
          child: Column(
            children: <Widget>[
              Padding(
                padding: EdgeInsets.fromLTRB(0, _height / 4.5, 0, 0),
                child: Text(
                  "X??c minh email",
                  style: TextStyle(color: Colors.black, fontSize: 28),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 30, 0, 10),
                child: TextField(
                  enabled: false,
                  controller: _emailController,
                  style: TextStyle(color: Colors.black, fontSize: 18),
                  decoration: InputDecoration(
                      labelText: "?????a ch??? email",
                      prefixIcon: Container(
                        padding: EdgeInsets.fromLTRB(11, 0, 11, 0),
                        width: 5,
                        child: Image.asset("ic_email.png"),
                      ),
                      border: OutlineInputBorder(
                          borderSide:
                              BorderSide(color: Color(0xffCED002), width: 1),
                          borderRadius: BorderRadius.all(Radius.circular(6)))),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 0, 0, 10),
                child: TextField(
                  controller: _reEmailController,
                  style: TextStyle(color: Colors.black, fontSize: 18),
                  decoration: InputDecoration(
                      labelText: "Nh???p l???i ?????a ch??? email",
                      prefixIcon: Container(
                        padding: EdgeInsets.fromLTRB(11, 0, 11, 0),
                        width: 5,
                        child: Image.asset("ic_email.png"),
                      ),
                      border: OutlineInputBorder(
                          borderSide:
                              BorderSide(color: Color(0xffCED002), width: 1),
                          borderRadius: BorderRadius.all(Radius.circular(6)))),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 20, 0, 20),
                child: SizedBox(
                  width: double.infinity,
                  height: 52,
                  child: RaisedButton(
                    color: Colors.blue,
                    onPressed: () {
                      _onSuccessClick();
                    },
                    child: Text("Ho??n th??nh",
                        style: TextStyle(color: Colors.white, fontSize: 18)),
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.all(Radius.circular(6))),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  _onSuccessClick() async {
    String reEmail = _reEmailController.text;
    LoadingDialog.showLoadingDialog(context, "??ang x??? l??...");
    if (email != reEmail) {
      LoadingDialog.hideLoadingDialog(context);
      MsgDialog.showMsgDialog(context, "Th??ng b??o", "Sai t??i kho???n email");
    } else {
      var auth = MyApp.of(context).auth;
      await auth.isEmailCorrect(username, email, () {}, (msg) {
        LoadingDialog.hideLoadingDialog(context);
        MsgDialog.showMsgDialog(context, "????ng nh???p", msg);
      }).then((value) {
        print("value : " + value);
        if (value == "true") {
          LoadingDialog.hideLoadingDialog(context);
          MsgDialog.showMsgDialogAndBackToLogin(
              context, "Th??nh c??ng", "Vui l??ng ki???m tra email");
        } else {
          LoadingDialog.hideLoadingDialog(context);
          MsgDialog.showMsgDialog(context, "Th???t b???i", "C?? l???i khi ki???m tra");
        }
      });
    }
  }
}
