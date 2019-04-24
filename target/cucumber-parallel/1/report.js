$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("/Users/grasbergerm/Projects/PMTAutomationFramework/src/test/resources/features/login.feature");
formatter.feature({
  "line": 2,
  "name": "Login and Logout",
  "description": "As a user\nI want to be login\nSo that I can access the site",
  "id": "login-and-logout",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@base"
    },
    {
      "line": 1,
      "name": "@login"
    },
    {
      "line": 1,
      "name": "@pmt"
    }
  ]
});
formatter.before({
  "duration": 2381005842,
  "status": "passed"
});
formatter.scenario({
  "line": 8,
  "name": "Able to login as new user",
  "description": "",
  "id": "login-and-logout;able-to-login-as-new-user",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 7,
      "name": "@ac-newtest"
    },
    {
      "line": 7,
      "name": "@acceptance"
    },
    {
      "line": 7,
      "name": "@smoke"
    }
  ]
});
formatter.step({
  "line": 9,
  "name": "I login",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "I am logged in",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {}
  ],
  "location": "LoginSteps.login(String)"
});
formatter.write("Pass. Expected: Browser is maximized\nActual: Browser is maximized\n");
formatter.write("Pass. Expected: All cookies are removed\nActual: All cookies are removed\n");
formatter.write("Pass. Expected: Loaded \u003ca target\u003d\u0027_blank\u0027 href\u003d\u0027https://mcqa.joinallofus.org\u0027\u003ehttps://mcqa.joinallofus.org\u003c/a\u003e\nActual: Loaded \u003ca target\u003d\u0027_blank\u0027 href\u003d\u0027https://mcqa.joinallofus.org\u0027\u003ehttps://mcqa.joinallofus.org\u003c/a\u003e in 1.918 seconds\n");
formatter.write("Pass. Expected:  element with \u003ci\u003eNAME\u003c/i\u003e of \u003ci\u003eemail\u003c/i\u003e  is present, displayed, and enabled to have text matthew.grasberger+pmt@coveros.com typed in\nActual: Typed text \u0027matthew.grasberger+pmt@coveros.com\u0027 in  element with \u003ci\u003eNAME\u003c/i\u003e of \u003ci\u003eemail\u003c/i\u003e \n");
formatter.write("Pass. Expected:  element with \u003ci\u003eNAME\u003c/i\u003e of \u003ci\u003epassword\u003c/i\u003e  is present, displayed, and enabled to have text Password123! typed in\nActual: Typed text \u0027Password123!\u0027 in  element with \u003ci\u003eNAME\u003c/i\u003e of \u003ci\u003epassword\u003c/i\u003e \n");
formatter.write("Pass. Expected: Element with \u003ci\u003eCLASSNAME\u003c/i\u003e of \u003ci\u003esubmit-button-login\u003c/i\u003e is displayed\nActual: Waited 0.145 seconds for  element with \u003ci\u003eCLASSNAME\u003c/i\u003e of \u003ci\u003esubmit-button-login\u003c/i\u003e  to be displayed\n");
formatter.write("Pass. Expected:  element with \u003ci\u003eCLASSNAME\u003c/i\u003e of \u003ci\u003esubmit-button-login\u003c/i\u003e  is present, displayed, and enabled to be clicked\nActual: Clicked  element with \u003ci\u003eCLASSNAME\u003c/i\u003e of \u003ci\u003esubmit-button-login\u003c/i\u003e \n");
formatter.write("Pass. Expected: Element with \u003ci\u003eNAME\u003c/i\u003e of \u003ci\u003eenter6DigitCode\u003c/i\u003e is present\nActual: Waited 0.573 seconds for  element with \u003ci\u003eNAME\u003c/i\u003e of \u003ci\u003eenter6DigitCode\u003c/i\u003e  to be present\n");
formatter.write("Pass. Expected:  element with \u003ci\u003eNAME\u003c/i\u003e of \u003ci\u003eenter6DigitCode\u003c/i\u003e  is present, displayed, and enabled to have text 987320 typed in\nActual: Typed text \u0027987320\u0027 in  element with \u003ci\u003eNAME\u003c/i\u003e of \u003ci\u003eenter6DigitCode\u003c/i\u003e \n");
formatter.write("Pass. Expected:  element with \u003ci\u003eCLASSNAME\u003c/i\u003e of \u003ci\u003esubmit-button-login\u003c/i\u003e  is present, displayed, and enabled to be clicked\nActual: Clicked  element with \u003ci\u003eCLASSNAME\u003c/i\u003e of \u003ci\u003esubmit-button-login\u003c/i\u003e \n");
formatter.write("Pass. Expected: Element with \u003ci\u003eXPATH\u003c/i\u003e of \u003ci\u003e//h1[text()\u003d\u0027User Administration\u0027]\u003c/i\u003e is present\nActual: Waited 1.607 seconds for  element with \u003ci\u003eXPATH\u003c/i\u003e of \u003ci\u003e//h1[text()\u003d\u0027User Administration\u0027]\u003c/i\u003e  to be present\n");
formatter.write("Pass. Expected: Element with \u003ci\u003eXPATH\u003c/i\u003e of \u003ci\u003e//h1[text()\u003d\u0027User Administration\u0027]\u003c/i\u003e is displayed\nActual: Waited 1.817 seconds for  element with \u003ci\u003eXPATH\u003c/i\u003e of \u003ci\u003e//h1[text()\u003d\u0027User Administration\u0027]\u003c/i\u003e  to be displayed\n");
formatter.result({
  "duration": 7442156457,
  "status": "passed"
});
formatter.match({
  "location": "LoginSteps.assertLoggedIn()"
});
formatter.write("Success: Expected: \nActual: Element with \u003ci\u003eID\u003c/i\u003e of \u003ci\u003emanager-account\u003c/i\u003e is displayed on the page\n");
formatter.result({
  "duration": 931226915,
  "status": "passed"
});
formatter.embedding("image/png", "embedded0.png");
formatter.after({
  "duration": 864201599,
  "status": "passed"
});
});