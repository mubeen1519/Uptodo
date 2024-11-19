package com.stellerbyte.uptodo.screens.category

//@Composable
//fun CategoryPage(navigate: (String) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
//
//    val iconLibraryState: MutableState<Boolean> = remember {
//        mutableStateOf(false)
//    }
//    val selectedValue = remember {
//        mutableStateOf(0)
//    }
//
//    val newValues = remember {
//        mutableStateOf("")
//    }
//
//
//    Column(modifier = Modifier.padding(10.dp)) {
//        Icons.values().forEachIndexed { index, icons ->
//            Text(
//                text = stringResource(id = R.string.new_category),
//                color = MaterialTheme.colorScheme.onSurface,
//                style = MaterialTheme.typography.bodyLarge
//            )
//
//            Spacer(modifier = Modifier.height(15.dp))
//
//            InputField(
//                value = icons.title.replace(icons.title, newValues.value, ignoreCase = true),
//                placeholderText = "Category name",
//                onFieldChange = {
//                    newValues.value = it
//                },
//                label = "Category name:"
//            )
//
//            Spacer(modifier = Modifier.height(15.dp))
//            Text(
//                text = stringResource(id = R.string.category_icon),
//                color = MaterialTheme.colorScheme.surfaceVariant
//            )
//
//            Spacer(modifier = Modifier.height(15.dp))
//            if (iconLibraryState.value) {
//                LibraryIcon(
//                    state = iconLibraryState,
//                    selectedItem = { selectedValue.value = it }
//                )
//            }
//            Button(
//                onClick = {
//                    iconLibraryState.value = true
//                },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.secondary,
//                    contentColor = MaterialTheme.colorScheme.onSurface
//                ),
//                shape = RoundedCornerShape(8.dp),
//            ) {
//                IconButton(onClick = {
//                    iconLibraryState.value = false
//                    selectedValue.value = index
//                }) {
//                    DrawableIcon(
//                        painter =
//                        painterResource(
//                            id = when (selectedValue.value) {
//                                Icons.Grocery.icon -> icons.icon
//                                Icons.University.icon -> icons.icon
//                                Icons.Work.icon -> icons.icon
//                                Icons.Sport.icon -> icons.icon
//                                Icons.Social.icon -> icons.icon
//                                Icons.Music.icon -> icons.icon
//                                Icons.Home.icon -> icons.icon
//                                Icons.Health.icon -> icons.icon
//                                Icons.Design.icon -> icons.icon
//                                Icons.Movie.icon -> icons.icon
//                                else -> Icons.Home.icon
//                            }
//                        ), contentDescription = "icons",
//                        modifier = Modifier.size(30.dp)
//                    )
//                }
//            }
//            Spacer(modifier = Modifier.height(15.dp))
//            Text(
//                text = stringResource(id = R.string.category_color),
//                color = MaterialTheme.colorScheme.surfaceVariant
//            )
//
//            Spacer(modifier = Modifier.height(15.dp))
//            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
//                EnumColors(itemWidth = 30.dp, onItemSelection = {
//                    selectedValue.value = it
//                })
//            }
//
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    modifier = Modifier.fillMaxSize()
//                ) {
//                    Text(
//                        text = "Cancel",
//                        color = Purple40,
//                        modifier = Modifier.clickable { navigate(Home) })
//
//                    Button(
//                        onClick = {
//                            viewModel.onIconChange(icons)
//                            navigate(Home)
//                        },
//                        modifier = Modifier.size(width = 150.dp, height = 40.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Purple40,
//                            contentColor = MaterialTheme.colorScheme.onSurface
//                        ),
//                        shape = RoundedCornerShape(8.dp)
//                    ) {
//                        Text(text = "Create Category", color = MaterialTheme.colorScheme.onSurface)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun EnumColors(
//    itemWidth: Dp,
//    defaultSelectedItemIndex: Int = 0,
//    onItemSelection: (selectedValue: Int) -> Unit,
//) {
//    val selectedIndex = remember {
//        mutableStateOf(defaultSelectedItemIndex)
//    }
//
//    Colors.values().forEachIndexed { index, item ->
//        Box(
//            modifier = Modifier
//                .size(itemWidth)
//                .clip(CircleShape)
//                .background(item.color)
//                .clickable {
//                    selectedIndex.value = index
//                    onItemSelection(selectedIndex.value)
//                },
//            contentAlignment = Alignment.Center
//        ) {
//            DrawableIcon(
//                painter = painterResource(id = item.icon),
//                contentDescription = "check",
//                tint = if (selectedIndex.value == index) MaterialTheme.colorScheme.background else Color.Transparent
//            )
//        }
//    }
//}