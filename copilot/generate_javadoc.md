You are generating Javadoc comments for Minecraft Mixin classes.

Your task has two parts:
1. Generate Javadocs for all Mixin methods.
2. Generate Javadocs for all @Unique fields.

Follow ALL rules below exactly.

====================================================================
PART 1 — JAVADOCS FOR MIXIN METHODS
====================================================================

Choose the template by the method's annotations:

- Methods carrying a Mixin injection or overwrite annotation (such as
  @Inject, @ModifyVariable, @ModifyArg, @Redirect, or @Overwrite) use the
  FULL template below.
- All other methods, including private helpers, @Unique helpers, and methods
  in nested helper types, use the SIMPLE template below.

FULL TEMPLATE:

     /**
      * @reason <simple one-sentence generalization of the method's purpose;
      *          no code references or links>
      *
      * @desc <1–3 sentence description of what the injected or overwritten
      *       code does. Use precise technical language describing the
      *       behaviour change, the modification being applied, and its
      *       effect on vanilla logic. Do NOT mention the injection type
      *       (Inject, Redirect, ModifyArg, etc.) or the injection point
      *       (HEAD, RETURN, TAIL, etc.).>
      */

SIMPLE TEMPLATE:

     /**
      * <simple one-sentence generalization of the method's purpose; no
      *   @reason label, no @desc, no code references, no links>
      */


STRICT RULES:
- Write in clear, concise, technical language.
- Apply the FULL template only to methods with Mixin injection or overwrite
  annotations; apply the SIMPLE template to every other method.
- SIMPLE-template Javadocs must be exactly one sentence with no tags, no
  blank lines, no @reason label, no @desc, no inline links, and no code
  references.
- @reason in FULL-template Javadocs MUST be a single simple sentence that
  generalizes the method's purpose. Do not include class names, method names,
  field names, packages, injection points, injection types, {@link ...}, or
  {@code ...} in @reason.
- Use inline Javadoc links only in @desc of FULL-template Javadocs; never in
  @reason or SIMPLE-template Javadocs.
- In @desc, reference code with inline Javadoc links, not plain text: use
  {@link Class}, {@link Class#member}, and {@link Class#method(paramType)}
  for overloads. Use {@code ...} for literals, property keys, and tokens
  that are not declarations.
- In @desc, never write bare qualified member names such as Main.main; write
  {@link Main#main(String[])}.
- In @desc, use fully qualified link targets when the referenced type is not
  imported by the mixin source file. Do not add imports only to shorten a link.
- Describe the actual behaviour change, not generalities.
- Explicitly state whether the mixin filters, cancels, replaces,
  modifies, overrides, alters initialization, or changes rendering or logic.
- For injections, state what is prevented, altered, or added; do not name the
  injection point.
- For overwrites, specify what vanilla logic is replaced and what the
  new logic accomplishes.
- If the mixin contains multiple methods, generate a SEPARATE Javadoc
  block for each method.
- FULL-template Javadocs use no tags other than @reason and @desc;
  SIMPLE-template Javadocs use no tags.
- No commentary outside the Javadoc block.
- Do not rewrite the method, class, or annotations.

OUTPUT FORMAT:
- Wrap each Javadoc block in its own ```java code block.
- Output ONLY the completed code-block-wrapped Javadoc blocks.

====================================================================
PART 2 — JAVADOCS FOR @Unique FIELDS
====================================================================

Locate every field annotated with @Unique.

For each @Unique field, generate a Javadoc block using EXACTLY this template:

    /**
     * @desc <clear technical description of the field’s purpose>
     * @spec <precise constraints, invariants, or acceptable value range>
     */

STRICT RULES:
- Only modify or add Javadocs for @Unique fields.
- Do NOT alter non-@Unique members.
- @desc must explain what the field represents and why it exists in the mixin.
- @spec must be a short, constraint‑style statement, not a descriptive sentence.
It MUST follow the format:
- Maintain concise, technical language.
- Use inline Javadoc links, not plain text, in @desc and @spec for code
  references; use fully qualified targets when the type is not imported.
- Infer purpose only from code context; do not invent behaviour.
- @spec must follow one of the following formats or something similarly concise such as:
    * @spec Range: 0.0–1.0
    * @spec Range: ≥ 0 (non‑negative)
    * @spec Constant; Immutable

OUTPUT FORMAT:
- Wrap each Javadoc block in its own ```java code block.
- Output ONLY the completed code-block-wrapped Javadoc blocks.

====================================================================
FINAL OUTPUT RULE
====================================================================

Return ONLY the generated Javadoc blocks (each inside ```java).
No explanations, no commentary, no extra text.
