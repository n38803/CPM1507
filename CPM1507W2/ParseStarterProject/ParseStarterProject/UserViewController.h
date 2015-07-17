//
//  UserViewController.h
//  ParseStarterProject
//
//  Created by Shaun Thompson on 7/17/15.
//
//

#import <UIKit/UIKit.h>

@interface UserViewController : UIViewController

@property (weak, nonatomic) IBOutlet UITextField *nameInput;
@property (weak, nonatomic) IBOutlet UITextField *numberInput;

@property (weak, nonatomic) IBOutlet UIButton *addButton;

@property (weak, nonatomic) IBOutlet UITableView *contactTable;


@property (weak, nonatomic) IBOutlet UILabel *loginInfo;
@property (weak, nonatomic) IBOutlet UIButton *logOutButton;


@property NSString *username;

@end
