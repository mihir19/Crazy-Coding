%% Credits to ShareLatex
%Latest compiler format
\NeedsTeXFormat{LaTeX2e}
%reference your own class file
\ProvidesClass{Mihir_Kavatkar-Resume}[05/10/2016 Mihir Kavatkar's class file]
% Importing packages only once
\LoadClass[oneside]{article}
\RequirePackage{titlesec}
\RequirePackage{geometry}
\geometry{
	letterpaper,
	left=1in,
	right=1in,
	top=1in,
	bottom=1in}
\RequirePackage{enumitem}
\RequirePackage{libertine}

% Formatting the section command
\titleformat{\section}
{\large\bfseries\scshape\centering\vspace*{-0.8em}}
{}{0em}
{}
[\titlerule]

% Customized subsection command
\titleformat{\subsection}
{\small\scshape\raggedright}
{}{0em}
{}

\titleformat{\subsubsection}
{\small\raggedright}
{}{0em}
{}

%% Need a Section/subsection title with date raggedright%%
%% Example:
%% University of Massachusetts                    Spring 2016 %%
\newcommand{\datedsection}[2]{
	\section[#1]{#1 \hfill #2}
}
\newcommand{\datedsubsection}[2]{
	\subsection[#1]{#1 \hfill #2}
}
%% subsection with City raggedright
\newcommand{\citysubsection}[2]{
	\subsection[#1]{#1 \hfill #2}
}

%% Adding content to the subsection with bullets %%
%% Following is for Bachelors Education
\newcommand{\educationinfoBachelors}[3]{
	\begin{itemize}[label={}]
		\small
		\itemsep0em
		\item #1
	\end{itemize}
}
%% Following is for Masters education
\newcommand{\educationinfoMasters}[3]{
	\begin{itemize}[label={}]
		\small
		\itemsep0em
		\item #1
		\item #2
		\item #3
	\end{itemize}
}
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%% Prints your name on top %%
\newcommand{\topname}[1]{
	\centerline{\Large{#1}}
}
%%  Command for Contact Information  %%
\newcommand{\contact}[5]{
	\centering{#1 {\large\textperiodcentered} #2 {\large\textperiodcentered} #3
		{\Large} #4 \ {\Large} #5}
}

%%  Following are commands for your description which gives bullets  %%
\newcommand{\workitems}[3]{
	\begin{itemize}
		\small
		\itemsep0em
		\item #1
		\item #2
		%\item #3
	\end{itemize}
}
\newcommand{\workitemsAdditional}[1]{
	\begin{itemize}
		\small
		\itemsep0em
		\item #1
		%\item #2
		%\item #3
	\end{itemize}
}
