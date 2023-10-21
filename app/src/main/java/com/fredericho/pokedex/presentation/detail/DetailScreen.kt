package com.fredericho.pokedex.presentation.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fredericho.pokedex.R
import com.fredericho.pokedex.data.pokemon.implementation.remote.response.AbilitiesItem
import com.fredericho.pokedex.data.pokemon.implementation.remote.response.DetailResponse
import com.fredericho.pokedex.data.pokemon.implementation.remote.response.StatsItem
import com.fredericho.pokedex.data.pokemon.implementation.remote.response.TypesItem
import com.fredericho.pokedex.presentation.detail.components.DetailTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    name: String,
    onNavigationBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel(),
) {

    val detailState by viewModel.detailState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getDetail(name)
    }

    when (val detail = detailState) {
        is DetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is DetailUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = detail.message,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is DetailUiState.Success -> {
            Scaffold(
                topBar = {
                    DetailTopBar(
                        onNavigationBack = { onNavigationBack() },
                        title = detail.detail.name.toString(),
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier

            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    CardContent(
                        pokemon = detail.detail,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .fillMaxHeight(0.81f)
                            .padding(4.dp),
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${detail.detail.sprites?.other?.home?.frontDefault}")
                            .placeholder(R.drawable.ic_launcher_foreground).crossfade(true).build(),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .size(200.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun CardContent(
    pokemon: DetailResponse,
    modifier: Modifier = Modifier,
) {
    Box(Modifier.fillMaxSize()) {
        Card(
            modifier = modifier
                .padding(top = 28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = stringResource(R.string.label_description),
                    modifier = Modifier
                        .padding(top = 24.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )

                DescriptionPokemon(
                    height = pokemon.height.toString(),
                    weight = pokemon.weight.toString(),
                    baseExperience = pokemon.baseExperience.toString(),
                )

                Text(
                    text = stringResource(R.string.label_type),
                    modifier = Modifier
                        .padding(top = 24.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    pokemon.types?.forEach { type ->
                        PokemonType(typePokemon = type!!)
                        Spacer(modifier = Modifier.padding(end = 8.dp))
                    }
                }

                Text(
                    text = stringResource(R.string.label_abilities),
                    modifier = Modifier
                        .padding(top = 24.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                ) {
                    pokemon.abilities?.forEach { type ->
                        ChipType(ability = type!!)
                        Spacer(modifier = Modifier.padding(end = 8.dp))
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                BaseStatsPokemon(stats = pokemon.stats)
            }
        }
    }
}

@Composable
fun DescriptionPokemon(
    height: String,
    weight: String,
    baseExperience: String,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(80.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_base_experience),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = baseExperience,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(80.dp)
            ) {
                Text(
                    text = stringResource(R.string.label_weight),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = weight,
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(80.dp)
            ) {
                Text(
                    text = stringResource(R.string.height),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = height,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BaseStatsPokemon(
    stats: List<StatsItem?>?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.label_base_stats),
            modifier = Modifier
                .padding(vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
        //status item
        FlowRow {
            stats?.forEach { status ->
                StatsItemRow(
                    title = status?.stat?.name.toString(),
                    value = status?.baseStat!!.toInt(),
                )
            }
        }
    }
}

@Composable
fun StatsItemRow(
    title: String,
    value: Int,
) {
    val progress by remember {
        mutableFloatStateOf(value.toFloat() / 100)
    }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = title,
    ).value

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = title,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth(0.25f)
        )
        Text(
            text = "0$value",
            fontSize = 10.sp,
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.End,
        )
        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipType(
    ability: AbilitiesItem
) {
    AssistChip(
        onClick = { },
        label = {
            Text(
                text = ability.ability?.name.toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 10.sp
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonType(
    typePokemon: TypesItem
) {
    AssistChip(
        onClick = { },
        label = {
            Text(
                text = typePokemon.type?.name.toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 10.sp
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    )
}
