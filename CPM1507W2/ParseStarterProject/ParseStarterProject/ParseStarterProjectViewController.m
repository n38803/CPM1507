//
//  ParseStarterProjectViewController.m
//
//  Copyright 2011-present Parse Inc. All rights reserved.
//

#import "ParseStarterProjectViewController.h"
#import "Parse/Parse.h"
#import "UserViewController.h"

@implementation ParseStarterProjectViewController

#pragma mark -
#pragma mark UIViewController

- (void)keyboardDidShow:(NSNotification *)notification
{
    //self.view.center = CGPointMake(self.originalCenter.x, 200);
}

- (void)keyboardDidHide:(NSNotification *)notification
{
    //self.view.center = self.originalCenter;
    
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event{
    [self.lpinput resignFirstResponder];
    [self.luinput resignFirstResponder];
    [self.ruinput resignFirstResponder];
    [self.rpinput resignFirstResponder];
}

- (BOOL)textFieldShouldBeginEditing:(UITextField *)textField{
    
    NSLog(@"Textfield: %@", textField);
    
    if (textField == _ruinput || textField == _rpinput){
        self.view.center = CGPointMake(self.originalCenter.x, 400);
    }
    return YES;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField{
    if (textField) {
        [textField resignFirstResponder];
        self.view.center = self.originalCenter;
    }
    
    return NO;
}


// IBACTIONS ------------------------------------

- (IBAction)login:(id)sender {
    
    self.username = self.luinput.text;
    self.password = self.lpinput.text;
    
    NSLog(@"You Logged %@",self.username);
    
    [self loginUser];
    
    
}

- (IBAction)register:(id)sender {
    
    self.username = self.ruinput.text;
    self.password = self.rpinput.text;
    
    NSLog(@"You Registered %@",self.ruinput.text);
    
    [self registerUser];
}

// ----------------------------------------------



// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
    
    // save view position
    self.originalCenter = self.view.center;
    
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardDidShow:) name:UIKeyboardDidShowNotification object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(keyboardDidHide:) name:UIKeyboardDidHideNotification object:nil];
    
    
    // Check to see if a user opted to stay logged in
    [self detectUser];
    
 
    

}

- (void) detectUser {
    
    [self loadUser];
}

- (void) loadUserPage {
    
    UserViewController *userViewController = [[UserViewController alloc] initWithNibName:nil bundle:nil];
    [self presentViewController:userViewController animated:YES completion:NULL];
    //[self.navigationController pushViewController:userViewController animated:YES];
    //[detailViewController release];  // Do that if you don't ARC.
    
}


- (IBAction)saveLogin:(id)sender {
    UISwitch *saveSwitch = (UISwitch *)sender;
    
    if ([saveSwitch isOn]) {
        _save = YES;
    } else {
        _save = NO;
    }
    
}


// LOGIN USER -----------------------------------

- (void) saveUser {
    NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    
    // saving an NSString
    [prefs setObject:_luinput.text forKey:@"username"];
    [prefs setObject:_lpinput.text forKey:@"password"];
    
    [prefs synchronize];
}

- (void) loadUser {
    NSUserDefaults *prefs = [NSUserDefaults standardUserDefaults];
    
    // getting an NSString
    _username = [prefs stringForKey:@"username"];
    _password = [prefs stringForKey:@"password"];
}

- (void)loginUser {
    
    [PFUser logInWithUsernameInBackground:self.username password:self.password block:^(PFUser *user, NSError *error) {
        if (user) {
            
            if (_save == YES){
                
                [self saveUser];
            }
            
            
            // Do stuff after successful login.
            UIAlertView *login = [[UIAlertView alloc] initWithTitle:[NSString stringWithFormat:@"Welcome %@", _username] message:@"Logging into Your Account ..." delegate:nil cancelButtonTitle:nil otherButtonTitles:nil];
            [login show];
            [self performSelector:@selector(test:) withObject:login afterDelay:1];
            [self loadUserPage];
            
            
        }
        else {
            
            // The login failed. Check error to see why.
            NSLog(@"Login ERROR!");
            
        }
        
    }];
    
}

-(void)test:(UIAlertView*)x{
    [x dismissWithClickedButtonIndex:-1 animated:YES];
}
// -----------------------------------------------


// REGISTER USER ---------------------------------
- (void)registerUser {
    PFUser *user = [PFUser user];
    user.username = self.username;
    user.password = self.password;
    
    
    
    [user signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (!error) {   // Hooray! Let them use the app now.
            
            // call login method
            [self loginUser];
            
            
        }
        else {
            
            NSString *errorString = [error userInfo][@"error"];
            NSLog(@"%@", errorString);
        }
    }];
    
    
}
// ----------------------------------------------







- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}


@end
