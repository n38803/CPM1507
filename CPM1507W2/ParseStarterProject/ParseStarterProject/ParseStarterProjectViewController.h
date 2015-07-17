//
//  ParseStarterProjectViewController.h
//
//  Copyright 2011-present Parse Inc. All rights reserved.
//

#import <UIKit/UIKit.h>

#import <Parse/Parse.h>

@interface ParseStarterProjectViewController : UIViewController

@property (weak, nonatomic) IBOutlet UIButton *lbutton;
@property (weak, nonatomic) IBOutlet UIButton *rbutton;

@property (weak, nonatomic) IBOutlet UITextField *luinput;
@property (weak, nonatomic) IBOutlet UITextField *lpinput;
@property (weak, nonatomic) IBOutlet UITextField *ruinput;
@property (weak, nonatomic) IBOutlet UITextField *rpinput;

@property NSString *username;
@property NSString *password;


@end
