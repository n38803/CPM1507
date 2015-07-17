//
//  ParseStarterProjectViewController.m
//
//  Copyright 2011-present Parse Inc. All rights reserved.
//

#import "ParseStarterProjectViewController.h"

#import <Parse/Parse.h>

@implementation ParseStarterProjectViewController

#pragma mark -
#pragma mark UIViewController


// IBACTIONS ------------------------------------

- (IBAction)login:(id)sender {
    
    self.username = self.luinput.text;
    self.password = self.lpinput.text;
    
    NSLog(@"You Logged %@",self.username);
    
    
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
    
    
    // Test object for Parse
    PFObject *testObject = [PFObject objectWithClassName:@"TestObject"];
    testObject[@"foo"] = @"bar";
    [testObject saveInBackground];
    
 
    

}

- (void) loadUserPage {
    
    UserViewController *userViewController = [[UserViewController alloc] initWithNibName:@"User" bundle:nil];
    
    [self.navigationController pushViewController:detailViewController animated:YES];
    //[detailViewController release];  // Do that if you don't ARC.
    
}


// LOGIN USER -----------------------------------

- (void)loginUser {
    
    [PFUser logInWithUsernameInBackground:self.username password:self.password block:^(PFUser *user, NSError *error) {
        if (user) {
            
            // Do stuff after successful login.
            
        }
        else {
            
            // The login failed. Check error to see why.
            
        }
        
    }];
    
}
// -----------------------------------------------


// REGISTER USER ---------------------------------
- (void)registerUser {
    PFUser *user = [PFUser user];
    user.username = self.username;
    user.password = self.password;
    
    // other fields can be set just like with PFObject
    user[@"phone"] = @"415-392-0202";
    
    [user signUpInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
        if (!error) {   // Hooray! Let them use the app now.
            
            // call login method
            [self loginUser];
            
            
        }
        else {   NSString *errorString = [error userInfo][@"error"];   // Show the errorString somewhere and let the user try again.
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
