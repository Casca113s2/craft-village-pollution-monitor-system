import 'package:fl_nynberapp/src/app.dart';
import 'package:fl_nynberapp/src/resources/craft_page.dart';
import 'package:fl_nynberapp/src/resources/dialog/loading_dialog.dart';
import 'package:fl_nynberapp/src/resources/dialog/msg_dialog.dart';
import 'package:fl_nynberapp/src/resources/forgot_password/user_forgot_pw_page.dart';
import 'package:fl_nynberapp/src/resources/home_page.dart';
import 'package:fl_nynberapp/src/resources/register_page.dart';
import 'package:fl_nynberapp/src/resources/theme/colors/light_colors.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:permission_handler/permission_handler.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LoginPage extends StatefulWidget {
  @override
  _LoginPageState createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  Map<Permission, PermissionStatus> permissions;
  void initState() {
    super.initState();
    _checkLoginStatus();
    _getPermission();
  }

  void _checkLoginStatus() async {
    SharedPreferences sharedPreferences = await SharedPreferences.getInstance();
    String token = sharedPreferences.getString("token");
    if (token != null) {
      Navigator.of(context).pushAndRemoveUntil(
          MaterialPageRoute(builder: (BuildContext context) => HomePage()),
          (Route<dynamic> route) => false);
    }
  }

  void _getPermission() async {
    permissions = await [
      Permission.location,
      Permission.camera,
      Permission.locationAlways,
    ].request();
  }

  TextEditingController _usernameController = new TextEditingController();
  TextEditingController _passController = new TextEditingController();

  //Build UI for login page
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: LightColors.kLightYellow3,
      body: Container(
        padding: EdgeInsets.fromLTRB(30, 0, 30, 0),
        constraints: BoxConstraints.expand(),
        child: SingleChildScrollView(
          //wrap t???t c??? th???ng con th??nh 1 scroll, tr??nh thi???t b??? b??? nh???
          child: Column(
            children: <Widget>[
              SizedBox(
                height: 110,
              ),
              Image.asset('ic_cv.png'),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 30, 0, 0),
                child: Text(
                  "Welcome Back!",
                  style: TextStyle(color: Colors.black, fontSize: 28),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 8, 0, 0),
                child: Text(
                  "Mobile App for collecting Craft Village Data",
                  style: TextStyle(color: Colors.grey, fontSize: 15),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 100, 0, 10),
                child: TextField(
                  controller: _usernameController,
                  style: TextStyle(color: Colors.black, fontSize: 18),
                  decoration: InputDecoration(
                      labelText: "T??n ????ng nh???p",
                      prefixIcon: Container(
                        padding: EdgeInsets.fromLTRB(11, 0, 11, 0),
                        width: 5,
                        child: Image.asset("ic_username.png"),
                      ),
                      border: OutlineInputBorder(
                          borderSide:
                              BorderSide(color: Color(0xffCED002), width: 1),
                          borderRadius: BorderRadius.all(Radius.circular(6)))),
                ),
              ),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 10, 0, 20),
                child: TextField(
                  controller: _passController,
                  obscureText: true, // format m???t kh???u th??nh d???u *
                  style: TextStyle(color: Colors.black, fontSize: 18),
                  decoration: InputDecoration(
                      labelText: "M???t kh???u",
                      prefixIcon: Container(
                        padding: EdgeInsets.fromLTRB(15, 0, 15, 0),
                        width: 5,
                        child: Image.asset("ic_password.png"),
                      ),
                      border: OutlineInputBorder(
                          borderSide:
                              BorderSide(color: Color(0xffCED002), width: 1),
                          borderRadius: BorderRadius.all(Radius.circular(6)))),
                ),
              ),
              Container(
                  alignment: AlignmentDirectional.centerEnd,
                  child: GestureDetector(
                    onTap: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => UserForgotPwPage()));
                    },
                    child: Text(
                      "Qu??n m???t kh???u?",
                      style: TextStyle(color: Colors.grey, fontSize: 15),
                    ),
                  )),
              Padding(
                padding: EdgeInsets.fromLTRB(0, 20, 0, 20),
                child: SizedBox(
                  width: double.infinity,
                  height: 52,
                  child: RaisedButton(
                    color: Colors.blue,
                    onPressed: _onClickLogin,
                    child: Text("????ng nh???p",
                        style: TextStyle(color: Colors.white, fontSize: 18)),
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.all(Radius.circular(6))),
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(0, 0, 0, 20),
                child: RichText(
                  text: TextSpan(
                      text: "Ch??a c?? t??i kho???n? ",
                      style: TextStyle(color: Colors.grey, fontSize: 15),
                      children: <TextSpan>[
                        TextSpan(
                            recognizer: TapGestureRecognizer()
                              ..onTap = () {
                                Navigator.push(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) => RegisterPage()));
                              },
                            text: "????ng k?? ngay n??o!",
                            style: TextStyle(color: Colors.blue, fontSize: 15)),
                      ]),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  //Trigger the event when user click login
  _onClickLogin() {
    String username = _usernameController.text;
    String pass = _passController.text;
    var auth = MyApp.of(context).auth;
    LoadingDialog.showLoadingDialog(context, "??ang x??? l??...");
    try {
      auth.signIn(username, pass, () {
        //LoadingDialog.hideLoadingDialog(context);
        //  Navigator.of(context)
        //     .push(MaterialPageRoute(builder: (context) => HomePage()));

        Navigator.of(context).pushAndRemoveUntil(
            MaterialPageRoute(builder: (BuildContext context) => HomePage()),
            (Route<dynamic> route) => false);
      }, (msg) {
        LoadingDialog.hideLoadingDialog(context);
        MsgDialog.showMsgDialog(context, "????ng nh???p", msg);
      });
    } catch (e) {
      print('l???i r???i'); 
      LoadingDialog.hideLoadingDialog(context);
      MsgDialog.showMsgDialog(context, "Th??ng b??o", "L???i m??y ch???, vui l??ng th??? l???i sau");
    }
  }
}
