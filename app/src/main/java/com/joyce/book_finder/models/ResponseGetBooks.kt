package com.joyce.book_finder.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseGetBooks(
	@SerializedName("books") val books: List<BookItem>,
	@SerializedName("navigation") val navigation: Navigation,
	@SerializedName("status") val status: Status,
) : Parcelable

@Parcelize
data class Status(
	@SerializedName("success") val success: Boolean? = null,
	@SerializedName("code") val code: Int? = null
): Parcelable

@Parcelize
data class Navigation(
	@SerializedName("page") val page: Int? = null,
	@SerializedName("next_page") val nextPage: Int? = null,
	@SerializedName("prev_page") val prevPage: Int? = null,
	@SerializedName("total_pages") val totalPages: Int? = null,
	@SerializedName("total_records") val totalRecords: Int? = null
): Parcelable

@Parcelize
data class BookItem(
	@SerializedName("isbn") val isbn: String? = null,
	@SerializedName("formato")val formato: String? = null,
	@SerializedName("titulo") val titulo: String? = null,
	@SerializedName("subtitulo") val subtitulo: String? = null,
	@SerializedName("titulo_original") val tituloOriginal: String? = null,
	@SerializedName("volume") val volume: String? = null,
	@SerializedName("edicao") val edicao: String? = null,
	@SerializedName("detalhes_da_edicao") val detalhesDaEdicao: String? = null,
	@SerializedName("ano_edicao") val anoEdicao: String? = null,
	@SerializedName("colecao") val colecao: String? = null,
	@SerializedName("volume_colecao") val volumeColecao: String? = null,
	@SerializedName("data_publicacao") val dataPublicacao: String? = null,
	@SerializedName("idioma") val idioma: String? = null,
	@SerializedName("origem") val origem: String? = null,
	@SerializedName("contribuicao") val contribuicao: List<ContribuicaoItem?>? = null,
	@SerializedName("catalogacao") val catalogacao: Catalogacao? = null,
	@SerializedName("bisac_principal") val bisacPrincipal: String? = null,
	@SerializedName("sinopse") val sinopse: String? = null,
	@SerializedName("sumario") val sumario: String? = null,
	@SerializedName("classificacao_indicativa") val classificacaoIndicativa: String? = null,
	@SerializedName("faixa_etaria") val faixa_etaria: String? = null,
	@SerializedName("ano_escolar") val ano_escolar: String? = null,
	@SerializedName("grau_escolar") val grau_escolar: String? = null,
	@SerializedName("materia_escolar") val materiaEscolar: String? = null,
	@SerializedName("link") val link: String? = null,
	@SerializedName("booktrailer") val booktrailer: String? = null,
	@SerializedName("medidas") val medidas: Medidas? = null,
	@SerializedName("dimensoes") val dimensoes: String? = null,
	@SerializedName("moeda") val moeda: String? = null,
	@SerializedName("ebook_drm") val ebookDrm: String? = null,
	@SerializedName("ebook_distribuicao") val ebook_distribuicao: String? = null,
	@SerializedName("status") val status: Int? = null,
	@SerializedName("previsao_disponibilidade") val previsaoDisponibilidade: String? = null,
	@SerializedName("codigo_de_barras") val codigoDeBarras: String? = null,
	@SerializedName("codigo_interno") val codigoInterno: String? = null,
	@SerializedName("certificacao_inmetro") val certificacaoInmetro: String? = null,
	@SerializedName("codigo_fiscal") val codigoFiscal: String? = null,
	@SerializedName("preco") val preco: String? = null,
	@SerializedName("registro_criado_em") val registroCriadoEm: String? = null,
	@SerializedName("registro_atualizado_em") val registroAtualizadoEm: String? = null,
	@SerializedName("imagens") val imagens: Imagens,
	@SerializedName("editora") val editora: Editora? = null,
	@SerializedName("selo") val selo: Selo? = null
) : Parcelable

@Parcelize
data class imagem_primeira_capa(
	@SerializedName("pequena") val pequena: String,
	@SerializedName("media") val media: String,
	@SerializedName("grande") val grande: String
) : Parcelable

@Parcelize
data class Selo(
	@SerializedName("codigo_selo") val codigoSelo: String? = null,
	@SerializedName("nome_do_selo_editorial") val nomeDoSeloEditorial: String? = null
) : Parcelable

@Parcelize
data class Medidas(
	@SerializedName("altura") val altura: String? = null,
	@SerializedName("largura") val largura: String? = null,
	@SerializedName("espessura") val espessura: String? = null,
	@SerializedName("peso") val peso: String? = null,
	@SerializedName("paginas") val paginas: String? = null,
	@SerializedName("encadernacao") val encadernacao: String? = null
) : Parcelable

@Parcelize
data class Catalogacao(
	@SerializedName("palavras_chave") val palavrasChave: String? = null,
	@SerializedName("areas") val areas: String? = null,
	@SerializedName("cdd") val cdd: String? = null,
	@SerializedName("bisac_principal") val bisacPrincipal: List<String?>? = null,
	@SerializedName("codigo_thema_categoria") val codigo_thema_categoria: List<String?>? = null,
	@SerializedName("codigo_thema_qualificador") val codigo_thema_qualificador: List<String?>? = null
) : Parcelable

@Parcelize
data class ContribuicaoItem(
	@SerializedName("nome") val nome: String? = null,
	@SerializedName("sobrenome") val sobrenome: String? = null,
	@SerializedName("tipo_de_contribuicao") val tipoDeContribuicao: String? = null,
	@SerializedName("codigo_contribuicao") val codigoContribuicao: String? = null
) : Parcelable

@Parcelize
data class Imagens(
	@SerializedName("imagem_primeira_capa") val imagem_primeira_capa: imagem_primeira_capa
) : Parcelable

@Parcelize
data class Editora(
	@SerializedName("codigo_editora") val codigoEditora: String? = null,
	@SerializedName("nome_fantasia") val nomeFantasia: String? = null,
	@SerializedName("cnpj") val cnpj: String? = null,
	@SerializedName("cidade") val cidade: String? = null
) : Parcelable
