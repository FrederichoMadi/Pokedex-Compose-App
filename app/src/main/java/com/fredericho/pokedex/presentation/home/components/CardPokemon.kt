package com.fredericho.pokedex.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fredericho.pokedex.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPokemon(
    modifier: Modifier = Modifier,
    name: String,
    onDetailPokemon: (String) -> Unit = {},
) {

    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickable { onDetailPokemon(name) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_pokemon),
                contentDescription = name,
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,

                )
        }
    }
}