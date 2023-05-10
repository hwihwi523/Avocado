import styled from "@emotion/styled";
type Style = {
  type?: string;
  size?: string;
  fontWeight?: number;
  color?: string;
  style?: React.CSSProperties;
};

const InlineText = ({
  type = "M",
  size = "1rem",
  fontWeight = 400,
  color = "black",
  children,
  style = {},
}: React.PropsWithChildren<Style>) => {
  return (
    <StyledSpan
      type={type}
      size={size}
      fontWeight={fontWeight}
      color={color}
      style={style}
    >
      {children}
    </StyledSpan>
  );
};

const StyledSpan = styled.span<Style>`
  font-family: SeoulNamsan${(props) => props.type};
  font-size: ${(props) => props.size};
  font-weight: ${(props) => props.fontWeight};
  color: ${(props) => props.color};
`;

export default InlineText;
