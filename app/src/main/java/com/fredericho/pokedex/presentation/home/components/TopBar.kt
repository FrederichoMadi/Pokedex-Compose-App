package com.fredericho.pokedex.presentation.home.components

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fredericho.pokedex.R

@Composable
fun TopBar(
    title: String,
    @DrawableRes icon: Int,
    field: String,
    expanded: Boolean,
    onValueChange: (String) -> Unit,
    onChangeExpanded: () -> Unit,
    onDismissExpanded: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .background(color = Red, shape = RectangleShape)
            .padding(top = 8.dp, bottom = 0.dp, start = 4.dp, end = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(50.dp)
                    .padding(horizontal = 8.dp),
            )
            Spacer(modifier = Modifier.padding(6.dp))
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 12.dp),

            ) {
            BasicTextField(
                modifier = Modifier
                    .height(40.dp),
                value = field,
                onValueChange = onValueChange,
                singleLine = true,
                textStyle = TextStyle(
                    fontWeight = FontWeight.Normal,
                ),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(100.dp))
                            .border(
                                width = 1.dp,
                                color = Color.Transparent,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .background(color = Color.White)
                            .fillMaxWidth(0.8f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Favorite icon",
                            tint = Red,
                            modifier = Modifier
                                .padding(start = 8.dp, end = 4.dp)
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        if (field.isEmpty()) {
                            Text(
                                text = "Search pokemon...",
                                fontWeight = FontWeight.Normal,
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Box{
                FilledIconButton(
                    onClick = { onChangeExpanded() },
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Transparent,
                    ),
                    shape = CircleShape,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(
                        Icons.Default.Menu, contentDescription = null, tint = Red,
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { onDismissExpanded() }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Number") },
                        onClick = {
                            Toast.makeText(context, "Number", Toast.LENGTH_SHORT).show()
                            onDismissExpanded()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Name") },
                        onClick = {
                            Toast.makeText(context, "Name", Toast.LENGTH_SHORT).show()
                            onDismissExpanded()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopAppBar() {
    Surface {
        TopBar(
            title = "Pokedex",
            icon = R.drawable.ic_pokemon,
            expanded = false,
            field = "",
            onValueChange = {},
            onChangeExpanded = {},
            onDismissExpanded = {}
        )
    }
}
